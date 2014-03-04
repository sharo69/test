package com.exercise.AndroidCamera;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class AndroidCamera extends Activity implements SurfaceHolder.Callback{

 Camera camera;
 SurfaceView surfaceView;
 SurfaceHolder surfaceHolder;
 boolean previewing = false;;
 
 String stringPath = "/sdcard/samplevideo.3gp";
 
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState); 
       setContentView(R.layout.activity_main);  
      
       Button buttonStartCameraPreview = (Button)findViewById(R.id.startcamerapreview);
       Button buttonStopCameraPreview = (Button)findViewById(R.id.stopcamerapreview);
       Button buttonShtrak = (Button)findViewById(R.id.shtrak);
      
       getWindow().setFormat(PixelFormat.UNKNOWN);
       surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
       surfaceHolder = surfaceView.getHolder();
       surfaceHolder.addCallback(this);
       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
      
       buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    if(!previewing){
     camera = Camera.open();
     if (camera != null){
      try {
       camera.setPreviewDisplay(surfaceHolder);
       camera.startPreview();
       previewing = true;
      } catch (IOException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
      }
     }
    }
   }});
      
       buttonStopCameraPreview.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    if(camera != null && previewing){
     camera.stopPreview();
     camera.release();
     camera = null;
     
     previewing = false;
    }
   }});
       
       buttonShtrak.setOnClickListener(new Button.OnClickListener(){
    	   @Override
    		   public void onClick(View v) {
    	    // TODO Auto-generated method stub
    	    if(camera != null && previewing){
    	     camera.takePicture(null, null, new PictureCallback() {
				
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					send(data);
					camera.startPreview();
					
				}
			});
    	     
    	     
    	    }
    	   }});
      
   }
  
  

 @Override
 public void surfaceChanged(SurfaceHolder holder, int format, int width,
   int height) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void surfaceCreated(SurfaceHolder holder) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void surfaceDestroyed(SurfaceHolder holder) {
  // TODO Auto-generated method stub
  
 }
 
 private void send(final byte[] data){
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
	//	       	  output.println("Hello from Android");   
	//	       	output.flush();
	//	       	output.close();
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