package com.berkhayta.utility;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class CodeGenerator {
	/*public static String generateActivationCode(){
		int codelength=5;
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		StringBuilder sb=new StringBuilder(codelength);
		int count=0;
		for (int i = 0; i < codelength; i++) {
			char c=uuid.charAt(i);
			if(c=='-') {
				count++;
				continue;
			}
			if(count>0 && count<codelength){
				sb.append(c);
				count++;
			}
			if(count>codelength){
				break;
			}
		}
		return sb.toString();
	}*/
	
	public static String generateActivationCode() {
		String string = UUID.randomUUID().toString();
		
		String[] split =string.split("-");
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String s : split) {
			stringBuilder.append(s.charAt(0));
		}
		
		return stringBuilder.toString();
	}
	public static String getActivationCode(){
		String uuid = UUID.randomUUID().toString();
		return Arrays.stream(uuid.split("-")).map(segment -> String.valueOf(segment.charAt(0)))
		             .collect(Collectors.joining());
	}
}