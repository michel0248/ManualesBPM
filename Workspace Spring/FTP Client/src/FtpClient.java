import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class FtpClient {
	
	public static void main(String args[]) {
		FtpClient f = new FtpClient();
		f.subirFTP("");
	}

	
	private String localFile = "src/main/resources/input.txt";
	private String remoteDir = "remote_sftp_test/";
	SSHClient client = new SSHClient();
	public boolean subirFTP(String datos) {

		Logger logger = java.util.logging.Logger.getLogger("FtpClient");

		//String fileName = System.currentTimeMillis() + ".json";
		
		

		try {
			
			//FileWriter myWriter = new FileWriter(fileName);

			String server = "demo.wftpserver.com";
			String user = "demo-user";
			String password = "demo-user";

			
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(server);
			client.authPassword(user, password);

			
			SFTPClient sftpClient = client.newSFTPClient();

			//sftpClient.put(localFile, remoteDir + "sshjFile.txt");

			sftpClient.close();
			

			/*myWriter.write(datos);
			myWriter.close();
			InputStream targetStream = new FileInputStream(new File(fileName));*/

			return true;

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString());
			return false;
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.toString());
			}
			
		}

	}

}
