package gedcom;

public class ErrorData {
	private String errorType;
	private String recordType;
	private String userStoryNumber;
	private String individualId;
	private String errorDetails;

	ErrorData(){}
	
	ErrorData(
			String errorType, 
			String recordType,	
			String userStoryNumber,
			String individualId,
			String errorDetails){
		this.errorType = errorType;
		this.recordType = recordType;
		this.userStoryNumber = userStoryNumber;
		this.individualId = individualId;
		this.errorDetails = errorDetails;
	}
	
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setUserStoryNumber(String userStoryNumber) {
		this.userStoryNumber = userStoryNumber;
	}

	public String getUserStoryNumber() {
		return this.userStoryNumber;
	}

	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}

	public String getIndividualId() {
		return this.individualId;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public String getErrorDetails() {
		return this.errorDetails;
	}

}
