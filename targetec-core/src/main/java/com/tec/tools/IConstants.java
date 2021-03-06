package com.tec.tools;

public interface IConstants {

	public interface QuestionType {
		public static final Long READING = 10000001l;
		public static final Long WRITING = 10000002l;
		public static final Long LISTENING = 10000003l;
		public static final Long SPEAKING = 10000004l;
	}

	public interface QuestionStyle {
		//READING
		public static final Long MULTICHOICE = 10000001l;
		public static final Long DRAGTOCORRECT = 10000002L;
		public static final Long DRAGTOSORT = 10000003l;
		public static final Long DROPDOWN = 10000004L;
		public static final Long FIVE = 10000005l;
		
		//LISTENING
		public static final Long SINGLETEXT = 10000006l;
		public static final Long MULTITEXT = 10000007l;
		public static final Long DIFFERTRANSC = 10000008l;
		public static final Long MULTICHOICE_L = 10000009l;
		
		//WRITING
		public static final Long WRITING_STYLE = 10000010l;
		
		//SPEAKING
		public static final Long SPEAKING_STYLE = 10000011L;
		public static final Long DESCRIBE_IMAGE = 10000012L;
		public static final Long REPEAT_SENTENCE = 10000013L;
	}
	
	public interface EmailTempate{
		public static final Long FIRST_LOGIN_CREDENTIALS = 10000001L;
	}
}
