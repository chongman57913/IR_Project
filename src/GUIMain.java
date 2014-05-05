/*
 the query input text is call: namebox
 the last query input text is call : numberbox
 the button call : search
 the middle text area is call: textresult
 
 */


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUIMain{
	private JFrame frame;
	private JPanel imagePanel;
	private ImageIcon background;
	
	public static void main(String args[]){
		GUIMain gui = new GUIMain();
		gui.run();
	}
	
	public GUIMain(){
		frame = new JFrame();
	}
	
	public void run() {
		
		//the frame part
		JFrame frame = new JFrame ("The Query serch engine");
		
		//set the backgroud image
		background = new ImageIcon("image/bg.jpg");
		JLabel Label = new JLabel(background);
		Label.setBounds(1, 1, 600,400);
		imagePanel = (JPanel) frame.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(new GridBagLayout());
		frame.getLayeredPane().setLayout(null);
		
		frame.getLayeredPane().add(Label, new Integer(Integer.MIN_VALUE));
		frame.setSize(563,363);
		
		frame.setResizable(false);//set the customer can set the size of frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//laber name
		JLabel query = new JLabel("<html><B><font color = #FFD130>Query :</font></B></html>");
		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = 0;
		c0.gridwidth = 1;
		c0.gridheight = 1;
		c0.fill = GridBagConstraints.BOTH;
		c0.anchor = GridBagConstraints.EAST;
		frame.add(query,c0);
		
		//laber number
		JLabel number = new JLabel("<html><B><font color = #FFD130> Last Query </font></B></html>");
		c0.gridx = 0;
		c0.gridy = 4;
		c0.gridwidth = 1;
		c0.gridheight = 1;
		c0.fill = GridBagConstraints.BOTH;
		c0.anchor = GridBagConstraints.EAST;
		frame.add(number,c0);
		
		//centext text area
		JTextArea textreult = new JTextArea(13,30);
		c0.gridx = 0;
		c0.gridy = 1;
		c0.gridheight = 3;
		c0.gridwidth = 5;
		c0.fill = GridBagConstraints.BOTH;
		textreult.setEnabled(false);
		c0.anchor=GridBagConstraints.CENTER;
		
		frame.add(textreult,c0);
		
		//make the text area scrollbar
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(textreult);
		frame.add(scroll,c0);
		
		//name field
		JTextField namebox = new JTextField(30);
		c0.gridx = 1;
		c0.gridy = 0;
		c0.gridwidth = 2;
		c0.gridheight = 1;
		c0.fill = GridBagConstraints.BOTH;
		c0.anchor = GridBagConstraints.WEST;
		namebox.addKeyListener(new KeyListener(){ //user press enter in text area, search
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					SearchAction();	
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) { }

			@Override
			public void keyTyped(KeyEvent arg0) { }
			
		});
		
		frame.add(namebox,c0);
		
		//number field
		JTextField numberbox = new JTextField(10);
		c0.gridx = 1;
		c0.gridy = 4;
		c0.gridwidth = 3;
		c0.gridheight = 1;
		c0.fill = GridBagConstraints.BOTH;
		c0.anchor = GridBagConstraints.CENTER;
		frame.add(numberbox,c0);
		
		
		//serach button 
		JButton search = new JButton(new ImageIcon("image/search_btn.png"));
		c0.gridx = 4;
		c0.gridy = 0;
		c0.gridwidth = 1;
		c0.gridheight = 1;
		c0.fill = GridBagConstraints.NONE;
		c0.anchor = GridBagConstraints.WEST;
		frame.add(search,c0);
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SearchAction();
			}
		});
		
		frame.setVisible(true);
		
	}
	
	private void SearchAction(){
		System.out.println("Search button clicked");
	}

}