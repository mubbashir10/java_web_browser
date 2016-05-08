import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.URL;
import java.util.Vector;

import java.sql.Timestamp;
import java.util.Date;

import org.fit.cssbox.swingbox.BrowserPane;
import org.fit.cssbox.swingbox.util.GeneralEvent;
import org.fit.cssbox.swingbox.util.GeneralEventListener;
import org.fit.cssbox.swingbox.util.GeneralEvent.EventType;
import org.fit.net.DataURLHandler;


public class Gui extends JFrame{	

	//topbar elements
	private JPanel topBar;
	private JPanel navigation;
	private JPanel actionBar;
	private JButton homeBtn;
	private static JTextField addressbar;
	private JButton goBtn;
	private JButton captureBtn;

	//contentArea elements
	private static JPanel contentArea;
	private BrowserPane swingbox = null;

	//bottom bar
	private JPanel bottomBar;
	private static JLabel status;

	//dataset
	private static Gui g;
	private String home;
	private String current;

	static StringBuilder str;
	

	//constructor
	public Gui(){
		
		//dataset
		home = "http://google.com";
		current = home;
		status = new JLabel("Status: Loading...");

		//sections
		topBar = new JPanel(new BorderLayout());
		contentArea = new JPanel(new BorderLayout());
		bottomBar = new JPanel(new BorderLayout());

		//topBar 
		navigation = new JPanel(new BorderLayout());
		

        //home button action
		homeBtn = new JButton("Go to Home");
		homeBtn.addActionListener(new java.awt.event.ActionListener(){

            public void actionPerformed(java.awt.event.ActionEvent e){

            	addressbar.setText(home);
                g.getContentPane().remove(contentArea);
                contentArea.removeAll();
                status.setText("Status: Loading...");

            	SwingWorker workerHome = new SwingWorker<JPanel, Void>(){

					@Override
					public JPanel doInBackground() {

				        contentArea = loadWebPage(home);
				        return contentArea;
				    }

				    @Override
				    public void done() {

				    	try{
				    		contentArea = get();
				    		g.add(contentArea, BorderLayout.CENTER);
			                g.getContentPane().invalidate();
							g.getContentPane().validate();
							g.getContentPane().repaint();
							current = addressbar.getText();
				    		status.setText("Status: Loading Done!");
				    	}
				    	catch(Exception o){
				    		status.setText("Status: Error fetching data");
				    	}
				    }

				};
				workerHome.execute();
            }
        });
		
		//addressbar action
		addressbar = new JTextField("http://google.com");
		addressbar.addActionListener(new java.awt.event.ActionListener(){

            public void actionPerformed(java.awt.event.ActionEvent e){

                g.getContentPane().remove(contentArea);
                contentArea.removeAll();
                status.setText("Status: Loading...");

                SwingWorker workerBar = new SwingWorker<JPanel, Void>(){

					@Override
					public JPanel doInBackground() {

				        contentArea = loadWebPage(addressbar.getText());
				        return contentArea;
				    }

				    @Override
				    public void done() {

				    	try{
				    		contentArea = get();
				    		g.add(contentArea, BorderLayout.CENTER);
			                g.getContentPane().invalidate();
							g.getContentPane().validate();
							g.getContentPane().repaint();
							current = addressbar.getText();
							addressbar.setText(current);
				    		status.setText("Status: Loading Done!");
				    	}
				    	catch(Exception o){
				    		status.setText("Status: Error fetching data");
				    	}
				    }

				};
				workerBar.execute();
            }
        });
		actionBar = new JPanel(new BorderLayout());
		
		//go button action
		goBtn = new JButton("Go");
		goBtn.addActionListener(new java.awt.event.ActionListener(){

            public void actionPerformed(java.awt.event.ActionEvent e){
                
                g.getContentPane().remove(contentArea);
                contentArea.removeAll();
                status.setText("Status: Loading...");

                SwingWorker workerBtn = new SwingWorker<JPanel, Void>(){

					@Override
					public JPanel doInBackground() {

				        contentArea = loadWebPage(addressbar.getText());
				        return contentArea;
				    }

				    @Override
				    public void done() {

				    	try{
				    		contentArea = get();
				    		g.add(contentArea, BorderLayout.CENTER);
			                g.getContentPane().invalidate();
							g.getContentPane().validate();
							g.getContentPane().repaint();
							current = addressbar.getText();
							addressbar.setText(current);
				    		status.setText("Status: Loading Done!");
				    	}
				    	catch(Exception o){
				    		status.setText("Status: Error fetching data");
				    	}
				    }

				};
				workerBtn.execute();
            }
        });

        //save button action
		captureBtn = new JButton("Save WebPage");
		captureBtn.addActionListener(new java.awt.event.ActionListener(){

            public void actionPerformed(java.awt.event.ActionEvent e){
            	java.util.Date date= new java.util.Date();
            	try(  PrintWriter out = new PrintWriter("dump_"+(new Timestamp(date.getTime()))+".html") ){
				    out.println(str.toString());
				}
				catch(Exception io){
					io.printStackTrace();
				}
		       
            }
        });

		actionBar.add(goBtn, BorderLayout.WEST);
		actionBar.add(captureBtn, BorderLayout.EAST);
		topBar.add(homeBtn, BorderLayout.WEST);
		topBar.add(addressbar, BorderLayout.CENTER);
		topBar.add(actionBar, BorderLayout.EAST);
		topBar.setBorder(BorderFactory.createEmptyBorder(10,15,15,15));

		//contentarea
		SwingWorker worker = new SwingWorker<JPanel, Void>(){

			@Override
			public JPanel doInBackground() {
		        contentArea = loadWebPage("http://google.com");
		        return contentArea;
		    }

		    @Override
		    public void done() {

		    	try{
		    		contentArea = get();
		    		add(contentArea, BorderLayout.CENTER);
		    		status.setText("Status: Loading Done!");
		    	}
		    	catch(Exception o){
		    		status.setText("Status: Error fetching data");
		    	}
		    }

		};
		worker.execute();
		
		//bottombar
		bottomBar.add(status,BorderLayout.WEST);
		add(contentArea, BorderLayout.CENTER);
		bottomBar.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

		//adding sections to main panel
		setLayout(new BorderLayout());
		add(topBar, BorderLayout.NORTH);
		add(bottomBar, BorderLayout.SOUTH);

		//configuring window
		setBounds(100,100,1100,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	


	}

	//load web page
	public JPanel loadWebPage(String address){

		try{

			//making request
			str = new StringBuilder();
			URL url = DataURLHandler.createURL(null, address);
        	BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                // System.out.println(inputLine);
                str.append(inputLine);
            }
			swingbox = new BrowserPane();
			swingbox.addHyperlinkListener(new JavaWebBrowserHyperlinkHandler(g));
			contentArea.add(new JScrollPane(swingbox),BorderLayout.CENTER);
			swingbox.setPage(url);

		}
		catch (Exception e) {
			JLabel error = new JLabel("Couldn't connect to the server - try again");
			error.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
			error.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
    		error.setForeground(Color.decode("#ff0000"));
			contentArea.add(error,BorderLayout.CENTER);
        }

        return contentArea;
	}

	
	//if hyperlinked is clicked
	public Gui(String tmp){

		g.getContentPane().remove(contentArea);
        contentArea.removeAll();
        status.setText("Status: Loading...");
        SwingWorker workerHyp = new SwingWorker<JPanel, Void>(){

			@Override
			public JPanel doInBackground() {
		        contentArea = loadWebPage(tmp);
		        return contentArea;
		    }

		    @Override
		    public void done() {

		    	try{
		    		contentArea = get();
		    		g.add(contentArea, BorderLayout.CENTER);
		    		g.getContentPane().invalidate();
					g.getContentPane().validate();
					g.getContentPane().repaint();
					addressbar.setText(tmp);
		    		status.setText("Status: Loading Done!");
		    	}
		    	catch(Exception o){
		    		status.setText("Status: Error fetching data");
		    	}
		    }

		};
		workerHyp.execute();
	}

	//main method
	public static void main(String[] args){

		g = new Gui();
	
	}

}