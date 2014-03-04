package com.example.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

  private final Context context;

  public PhotoHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onPictureTaken(final byte[] data, Camera camera) {

	  Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		      
		       	  
		       	  Socket socket;
		       	try {
		       		
		       		SocketAddress sockaddr = new InetSocketAddress("192.168.0.101", 4747);
		       		socket = new Socket();
		       		socket.connect(sockaddr, 5000); //10 second connection timeout
		       		//socket = new Socket("192.168.0.101", 4747);
		       	  

		       	  OutputStream out = socket.getOutputStream();       
		       	//  PrintWriter output = new PrintWriter(out);         


		       	  
		       	  //editText.setText("Sending Data to PC");         
//		       	  output.println("Hello from Android");   
//		       	output.flush();
//		       	output.close();
		       	out.write(data);
		       	  out.flush();
		       	  out.close();
		       	  //editText.setText("Data sent to PC");            

		       	  socket.close();                                    
		       	  //editText.setText("Socket closed");    
		       	  
		       	} catch (Exception e) {
		       		// TODO Auto-generated catch block
		       		e.printStackTrace();
		       	}
		   }
		});

		thread.start();     
  }
} 