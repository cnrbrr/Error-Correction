package Codes;

public class InterLeaver {
	public int[][] data;
	public int[][] checkData;
	public int size;
	public int count;
	
	public InterLeaver(int size, int count){
		this.size = size;
		this.count = count;
		setTheSize();
	}
	
	public void setTheSize(){
		data = new int[size][count];//sets the initial sizes
		checkData = new int[size][count];
	}
	
	public void setTheData(int[] currentRow, int index){
		for(int i = 0; i < count; i++){//inserts data to both
			data[index][i] = currentRow[i];
			checkData[index][i] = currentRow[i]; 
		}
	}
	
	public void updateTheData(int[]currentRow, int index){
		for(int i = 0; i < count; i++){//only updates data
			data[index][i] = currentRow[i];
		}
	}
	
	public void printData(){//pretty print
		for(int i = 0; i < size; i++){
			for(int j = 0; j < count; j++){
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printcheckData(){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < count; j++){
				System.out.print(checkData[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int checkErrors(){
		int errorsLeft = 0;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < count; j++){
				if(data[i][j] == checkData[i][j]){//calculates the number of errors					
				}else{
					errorsLeft++;
				}
			}
		}
		
		return errorsLeft;
	}
	
	
}
