package app.system.application.backend.response;

public class CommonResponse<T> {
	
	  private T dataBundle;

	    private Boolean isSuccess;
	    private String errorMessage;
	    private String successMessage;


	    public CommonResponse(String errorMessage, T dataBundle) {

	        this.dataBundle = dataBundle;

	        this.errorMessage = errorMessage;
	    }

	    public CommonResponse(Boolean isSuccess, String errorMessage, T dataBundle) {

	        this.dataBundle = dataBundle;
	        this.isSuccess = isSuccess;
	        this.errorMessage = errorMessage;
	    }

	    public CommonResponse(String errorMessage) {

	        this.errorMessage = errorMessage;
	    }

	    public CommonResponse(Boolean isSuccess, String errorMessage) {

	        this.isSuccess = isSuccess;
	        this.errorMessage = errorMessage;
	    }


	    public String getErrorMessage() {
	        return errorMessage;
	    }

	    public void setErrorMessage(String errorMessage) {
	        this.errorMessage = errorMessage;
	    }

	    public T getDataBundle() {
	        return dataBundle;
	    }

	    public void setDataBundle(T dataBundle) {
	        this.dataBundle = dataBundle;
	    }

	    public Boolean getIsSuccess() {
	        return isSuccess;
	    }

	    public void setIsSuccess(Boolean isSuccess) {
	        this.isSuccess = isSuccess;
	    }

	    public String getSuccessMessage() {
	        return successMessage;
	    }

	    public void setSuccessMessage(String successMessage) {
	        this.successMessage = successMessage;
	    }

}
