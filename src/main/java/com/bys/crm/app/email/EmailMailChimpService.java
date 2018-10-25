package com.bys.crm.app.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.bys.crm.app.dto.EmailMailChimpDto;
import com.bys.crm.app.dto.RecipientsInfoDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.event.EmailMailChimpEvent;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.MandatoryException;
import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.MailchimpException;
import com.ecwid.maleorang.MailchimpObject;
import com.ecwid.maleorang.method.v3_0.batches.StartBatchMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignActionMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignActionMethod.Schedule;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo;
import com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo.TrackingInfo;
import com.ecwid.maleorang.method.v3_0.campaigns.EditCampaignMethod;
import com.ecwid.maleorang.method.v3_0.campaigns.content.SetCampaignContentMethod;
import com.ecwid.maleorang.method.v3_0.lists.CampaignDefaultsInfo;
import com.ecwid.maleorang.method.v3_0.lists.ContactInfo;
import com.ecwid.maleorang.method.v3_0.lists.EditListMethod;
import com.ecwid.maleorang.method.v3_0.lists.ListInfo;
import com.ecwid.maleorang.method.v3_0.lists.members.EditMemberMethod;
import static com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo.Type.PLAINTEXT;

@Service
public class EmailMailChimpService {

	@Value("${apiKey}")
	private  String apikey;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	public String sendEmail(Integer employeeId, EmailMailChimpDto emailDto){

		MailchimpClient client = new MailchimpClient(this.apikey);

		String mess = "";
		try{
			// create new CreateList
			EditListMethod.Create editListMethod = new EditListMethod.Create();
			editListMethod.name = "Danh sách chiến dịch: " + emailDto.getSubject();
			editListMethod.permission_reminder = "Bạn là đối tượng công ty chúng tôi quan tâm. ";
			editListMethod.email_type_option = false;

			// Create contactInfo
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.company = "Vinh Mỹ";
			contactInfo.city = "Hồ Chí Minh";
			contactInfo.address1 = "Quận Tân Bình, Hồ Chí Minh";
			contactInfo.country = "VietNam";
			contactInfo.zip = "+84";
			contactInfo.state = "Ho Chi Minh";

			// CampaignDefaultInfo
			CampaignDefaultsInfo campaignDefaultsInfo = new CampaignDefaultsInfo();
			campaignDefaultsInfo.from_name = emailDto.getSender();
			campaignDefaultsInfo.subject = emailDto.getSubject();
			campaignDefaultsInfo.from_email = "support@bys.vn";
			campaignDefaultsInfo.language = "en";

			// Implement
			editListMethod.contact = contactInfo;
			editListMethod.campaign_defaults = campaignDefaultsInfo;

			// Run
			ListInfo listInfo = client.execute(editListMethod);

			// System.out.println("listInfo.id "+listInfo.id);

			ArrayList<EditMemberMethod.CreateOrUpdate> emailList = new ArrayList<>();

			if (emailDto.getRecipients() != null && emailDto.getRecipients().size() > 0) {
				for (RecipientsInfoDto recipient : emailDto.getRecipients()) {
					EditMemberMethod.CreateOrUpdate createOrUpdate = new EditMemberMethod.CreateOrUpdate(listInfo.id,
							recipient.getEmail());
					createOrUpdate.status = "subscribed";
					createOrUpdate.merge_fields = new MailchimpObject();
					createOrUpdate.merge_fields.mapping.put("FNAME", recipient.getName());
					createOrUpdate.merge_fields.mapping.put("LNAME", recipient.getName());
					emailList.add(createOrUpdate);
				}
			}

			String id = client.execute(new StartBatchMethod(emailList)).id;

			System.out.println("StartBatchMethod.id " + id);

			EditCampaignMethod.Create campaign = new EditCampaignMethod.Create();
			campaign.type = PLAINTEXT;
			campaign.settings = new CampaignInfo.SettingsInfo();
			campaign.settings.mapping.put("title", emailDto.getTitle());
			campaign.settings.mapping.put("subject_line", emailDto.getSubject());
			campaign.settings.mapping.put("from_name", emailDto.getSender());
			campaign.settings.mapping.put("reply_to", "support@bys.vn");
			TrackingInfo track = new TrackingInfo();
			track.opens = true;
			track.text_clicks = true;
			track.text_clicks = true;
			campaign.tracking = track;

			// set List receiver
			CampaignInfo.RecipientsInfo recipientsInfo = new CampaignInfo.RecipientsInfo();
			recipientsInfo.list_id = listInfo.id;

			campaign.recipients = recipientsInfo;

			CampaignInfo campaignInfo = client.execute(campaign);
			// System.out.println("campaignInfo.id "+campaignInfo.id);
			// Set content email
			SetCampaignContentMethod setCampaignContentMethod = new SetCampaignContentMethod(campaignInfo.id);
			setCampaignContentMethod.plain_text = emailDto.getContent();
			client.execute(setCampaignContentMethod);
			System.out.println("sendEmail campaignInfo.id " + campaignInfo.id);
			
			if(campaignInfo != null && campaignInfo.id != null){
				EmailMailChimpEvent mailEvent = new EmailMailChimpEvent(this, campaignInfo);
				publisher.publishEvent(mailEvent);
			}
			mess = StatusMessengerEnum.Successful.name();
		}catch (MailchimpException e) {
			throw new InvalidException(e.getMessage(), ErrorCodeEnum.SEND_EMAIL_ERROR);
		} catch (IOException e) {
			throw new InvalidException(
					"Error sending email with mailchimp",
					ErrorCodeEnum.SEND_EMAIL_ERROR);
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return mess;
	}

	public String runSendSchedule(String campaignInfoID) throws IOException, MailchimpException {
		// Run campaign
		MailchimpClient client = new MailchimpClient(this.apikey);
				
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println("OLD calendar.getTime() "+calendar.getTime());
        int round = calendar.get(Calendar.MINUTE) % 15;
        System.out.println("round "+round);
        calendar.add(Calendar.MINUTE,round > 12 ? (30-round) : (15-round));
        calendar.set(Calendar.SECOND, 0);
        System.out.println("New calendar.getTime() "+calendar.getTime());
        
        CampaignActionMethod.Schedule schedule = new Schedule(campaignInfoID);
		schedule.schedule_time = calendar.getTime();
		client.execute(schedule);

		return StatusMessengerEnum.Successful.name();
	}

	public void runSend(CampaignInfo campaignInfo) {
		// Run campaign
		MailchimpClient client = new MailchimpClient(this.apikey);
		try{
			TimeUnit.MINUTES.sleep(5);
			System.out.println("runSend campaignInfo.id " + campaignInfo.id);			
			CampaignActionMethod.Send send = new CampaignActionMethod.Send(campaignInfo.id);
			client.execute(send);
		}catch (InterruptedException e){ 
			throw new InvalidException(e.getMessage(), ErrorCodeEnum.SEND_EMAIL_ERROR);
		}catch (MailchimpException e) {
			throw new InvalidException(e.getMessage(), ErrorCodeEnum.SEND_EMAIL_ERROR);
		} catch (IOException e) {
			throw new InvalidException(
					"Error sending email with mailchimp",
					ErrorCodeEnum.SEND_EMAIL_ERROR);
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
