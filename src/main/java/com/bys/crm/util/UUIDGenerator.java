package com.bys.crm.util;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.UUIDTimer;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDGenerator {

	public static UUID getTimeBasedUUID() {
		TimeBasedGenerator timeBasedGenerator = Generators
				.timeBasedGenerator(EthernetAddress.fromInterface());

		return timeBasedGenerator.generate();
	}

	public static UUID getTimeBasedUUID(long timestamp) {
		UUIDTimer timer;
		try {
			timer = new UUIDTimer(new java.util.Random(timestamp), null);
			TimeBasedGenerator generator = new TimeBasedGenerator(EthernetAddress.fromInterface(), timer);		
			return generator.generate();
			
		} catch (IOException e) {
			 throw new IllegalArgumentException("Failed to create UUIDTimer with specified synchronizer: "+e.getMessage(), e);
		}
		
	}
	
}
