package shared.communication;

import shared.model.Image;

public class GetSampleImageResult {

	private Image image;

	public GetSampleImageResult(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String toString(){
		String result = image.getFile() + "\n";
		return result;
	}
	
	public boolean equals(GetSampleImageResult output){
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
