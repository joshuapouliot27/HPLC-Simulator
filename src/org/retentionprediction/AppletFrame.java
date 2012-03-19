package org.retentionprediction;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class AppletFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected RetentionPredictorApplet myApplet;
	
	public AppletFrame(String str)
	{
		super(str);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    addWindowListener(new WindowAdapter()
	    {
	       public void windowClosing(WindowEvent e)
	       {
	         System.exit(0); //calling the method is a must
	       }
	    });
	}

	public static void main(String[] args) 
	{
		AppletFrame myFrame = new AppletFrame("GC Retention Predictor Demo"); // create frame with title
	    myFrame.setResizable(true);
	    
	    myFrame.myApplet = new RetentionPredictorApplet(); // define applet of interest

	    // add applet to the frame
	    myFrame.add(myFrame.myApplet, BorderLayout.CENTER);
	    // Call applet's init method (since Java App does not
	    // call it as a browser automatically does)
	    myFrame.pack(); // set window to appropriate size (for its elements)
	    myFrame.myApplet.init();	
	    myFrame.setVisible(true); // usual step to make frame visible
	
	} // end main
} // end class