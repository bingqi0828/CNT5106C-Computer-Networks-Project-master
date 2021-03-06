import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
    String filename;
    static Config config=new Config();
    private RandomAccessFile file;
	static int fileSize;
	static int pierceSize;
	static int total_piece_size;

    FileManager(String filename) throws Exception {
		this.filename = filename;
		fileSize=config.getFileSize();
		pierceSize=config.getPieceSize();
		total_piece_size=divdier(fileSize,pierceSize);
    }

    static int divdier(int x, int y){
		int final_result=0;
		if (x%y==0){
			final_result=x/y;
		}else{
			final_result=x/y+1;
		}
    	return final_result;
    	
    }
    
    static int calculate_length(int index){
    	int pierce_length=pierceSize;
    	if (index==total_piece_size){
    		pierce_length=fileSize-(index-1)*pierceSize;
    	}else{
    		pierce_length=pierceSize;
    	}
    	return pierce_length;
    }
    //return the data for that pierce
    
    //index here means the number of the data pierce.  MIGHT REDUCE 1!!!!!!! 
    public byte [] getPiece(int index) throws IOException {
    	/*
    	 * might need some adjustments. Refer to https://github.com/YeechingTiger/Network/blob/master/FileChunk.java
    	 */
    	
    	int fileSize=config.getFileSize();
    	int pierceSize=config.getPieceSize();
    	int total_piece_size=divdier(fileSize,pierceSize); 
    	
    	int pierce_length=calculate_length(index);
    	
    	byte[] pierce_needed=new byte[pierce_length];
    	
    	byte[] requested_data=new byte[pierce_length];
    	
    	file=new RandomAccessFile(filename,"rw");
    	long place= (long) (index-1)*pierceSize;
   
    	
		file.seek(place);
    	file.read(pierce_needed);
    	
    	System.arraycopy(pierce_needed, 0, requested_data, 0,pierce_needed.length);
    	
        return requested_data;
    }
    
    //will be called when the peer received such a pierce to write the data into disk
    public void setPiece(int index, byte [] payload) throws IOException {
    	
    	String filename="/Users/qibing/Desktop/peer_"+peerProcess.id;
    	File newfile=new File(filename);
    	if (!newfile.exists()){
    		newfile.createNewFile();
    	}
    		
    	RandomAccessFile file1=new RandomAccessFile(filename,"rw");
    	int pierce_length=calculate_length(index);
    	
    	long place=(long)(index*pierce_length);
    	file1.seek(place);
    	
    	file1.write(payload);
    	
        return;
    }
    
    public static void main(String[] args) throws Exception{
    	//System.out.println(divdier(10000232,32768));//
    	//System.out.println(config.getFileSize());
    /*
     * Testing part is shown below.	
     
    	RandomAccessFile file = new RandomAccessFile("1.txt","rw");
    	file.seek(4);
    	byte[] b=new byte[2];
    	byte[] b1=new byte[2];
    	byte[] b2=new byte[2];
    	byte[] b3=new byte[2];
    	byte[] b4=new byte[2];
    	byte[] b5=new byte[2];
    	byte[] b6=new byte[2];
    	byte[] b7=new byte[2];
    	byte[] b8=new byte[2];
    	byte[] b9=new byte[2];
    	
    	file.read(b);
    	file.seek(0);
    	file.read(b1);
    	file.seek(2);
    	file.read(b2);
    	
    	file.seek(6);
    	file.read(b3);
    	
    	file.seek(8);
    	file.read(b8);
    	
    	file.seek(16);
    	file.read(b4);
    	file.seek(14);
    	file.read(b7);
    	file.seek(12);
    	file.read(b6);
    	file.seek(10);
    	file.read(b5);
    	
    	System.arraycopy(b, 0, b9, 0, 2);
    	
    	System.out.println(b9[0]);
    	System.out.println(b9[1]);
    	
    	String filename="2.txt";
    	
    	File newfile=new File(filename);
    	if (!newfile.exists()){
    		newfile.createNewFile();
    	}
    	
    	RandomAccessFile file1=new RandomAccessFile(filename,"rw");
    	file1.seek(4);
    	file1.write(b);
    	file1.seek(0);
    	file1.write(b1);
    	file1.seek(2);
    	file1.write(b2);
    	file1.seek(8);
    	file1.write(b8);
    	file1.seek(16);
    	file1.write(b4);
    	
    	file1.seek(10);
    	file1.write(b5);
    	
    	file1.seek(12);
    	file1.write(b6);
    	file1.seek(6);
    	file1.write(b3);
    	
    	file1.seek(14);
    	file1.write(b7);
   
    	*/
    	
    	//System.out.println(file.read());
    	
    	//FileManager filemanager=new FileManager("1");
    	//System.out.println(filemanager.calculate_length(306));
    	//int fileSize=config.getFileSize();
    	//int pierceSize=config.getPieceSize();
    	//int total_piece_size=divdier(fileSize,pierceSize); 
    	//System.out.println(total_piece_size);
    	
    }
}
