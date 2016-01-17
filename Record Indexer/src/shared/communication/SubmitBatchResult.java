package shared.communication;

public class SubmitBatchResult {

	private boolean successful;

	public SubmitBatchResult(boolean successful) {
		this.successful = successful;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public String toString(){
		if(successful){
			return "TRUE\n";
		}
		else{
			return "FAILED\n";
		}
	}
	
	public boolean equals(SubmitBatchResult output){
		if(this == null || output == null){
			return (this == null && output == null);
		}
		if(this.toString().equals(output.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
