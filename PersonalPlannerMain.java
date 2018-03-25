import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class PersonalPlannerMain
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("plis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();

		//General variables
		JPanel x;
		GridBagConstraints gbc = new GridBagConstraints();
		TitledBorder b;
		Font largerFont = new Font(Font.SANS_SERIF,Font.BOLD, 14);
		int indent = 20;

		//Go To Notebook
		JPanel goToMenu = new JPanel();
		JFileChooser fcGoTo = new JFileChooser();
		fcGoTo.setCurrentDirectory(new java.io.File("."));
		fcGoTo.setDialogTitle("Display Files");

		fcGoTo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fcGoTo.setApproveButtonText("Go To Notebook");
		fcGoTo.setApproveButtonToolTipText("Go to selected notebook");
		goToMenu.add(fcGoTo);

		fcGoTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				if(event.getActionCommand().equals("CancelSelection"))
				{
				}
				else if (event.getActionCommand().equals("ApproveSelection"))
				{
					String DIRNAME = (fcGoTo.getSelectedFile()).getName();
					JFrame frame=new JFrame(DIRNAME+" MAIN_FRAME");

					frame.setSize(600, 600);
					frame.setVisible(true);

					JPanel panel=new JPanel();

					//Main Buttons
					JButton btn=new JButton("Create an Entry");
					JButton btn1=new JButton("Edit an Entry");
					JButton btn2=new JButton("Remove an Entry");
					JButton btn3=new JButton("Display Contents of Entry");

					panel.add(btn);
					panel.add(btn1);
					panel.add(btn2);
					panel.add(btn3);
					frame.add(panel);
					frame.pack();

					//Set onAction Listeners

					//CREATE ENTRY
					btn.addActionListener(new ActionListener() 
							{

								@Override
								public void actionPerformed(ActionEvent arg0) 
								{
									JFrame frame2=new JFrame("SEC_FRAME");
									JPanel panel2=new JPanel();
									frame2.add(panel2);
									frame2.setVisible(true);


									//Radio Button
									//TYPE OF ENTRY
									JLabel lab=new JLabel("Choose the type of entry");
									JRadioButton general = new JRadioButton("General ");
									JRadioButton todo = new JRadioButton("To Do List");
									JRadioButton textentry = new JRadioButton("Text Entry");

									JButton button=new JButton("OK");
									ButtonGroup group = new ButtonGroup();
									group.add(general);
									group.add(todo);

									frame2.setLayout(new FlowLayout());
									frame2.add(lab);
									frame2.add(general);
									frame2.add(todo);
									frame2.add(textentry);
									frame2.add(button);
									frame2.pack();

									//Action Listener
									button.addActionListener(new ActionListener() 
											{
												@Override
												public void actionPerformed(ActionEvent event) 
												{
													if(general.isSelected())
													{

														String s = JOptionPane.showInputDialog(frame, "Enter General Entry Name", "Creating General Entry", JOptionPane.PLAIN_MESSAGE);
														if(s!=null)
														{
															//Creating file
															File f = new File(DIRNAME+"//"+s+".en");
															if(!f.exists())
															{
																//Creating General Entry
																try
																{
																	f.createNewFile();
																	infoBox(frame, "General Entry "+s+" was successfully created!", "Creation Successful");
																}
																catch(IOException e)
																{
																	System.out.println(e.getMessage());
																}
															}
															else
															{
																infoBox(frame, "General Entry "+s+" already exists.", "Error");
															}
														}
													}
													else if(todo.isSelected())
													{
														String s = JOptionPane.showInputDialog(frame, "Enter To Do List Entry Name", "Creating To Do List Entry", JOptionPane.PLAIN_MESSAGE);
														if(s!=null)
														{
															//Creating file
															File f = new File(DIRNAME+"//"+s+".td");
															if(!f.exists())
															{
																//Creating General Entry
																try
																{
																	f.createNewFile();
																	ArrayList<String> al=new ArrayList <String>();
																	try{
																		FileOutputStream fos=new FileOutputStream(f);
																		ObjectOutputStream oos= new ObjectOutputStream(fos);
																		oos.writeObject(al);
																		oos.close();
																		fos.close();
																	}catch(Exception ex)
																	{
																		System.out.println(ex);
																	}


																	infoBox(frame, "To Do List Entry "+s+" was successfully created!", "Creation Successful");
																}
																catch(IOException e)
																{
																	System.out.println(e.getMessage());
																}
															}
															else
															{
																infoBox(frame, "To Do List Entry "+s+" already exists.", "Error");
															}
														}
													}
													else if(textentry.isSelected())
													{		String s = JOptionPane.showInputDialog(frame, "Enter Text Entry Name", "Creating Text Entry", JOptionPane.PLAIN_MESSAGE);
														if(s!=null)
														{
															//Creating file
															File f = new File(DIRNAME+"//"+s+".txt");
															if(!f.exists())
															{
																//Creating General Entry
																try
																{
																	f.createNewFile();
																	infoBox(frame, "Text Entry "+s+" was successfully created!", "Creation Successful");
																}
																catch(IOException e)
																{
																	System.out.println(e.getMessage());
																}

															}
															else
															{
																infoBox(frame, "Text Entry "+s+" already exists.", "Error");
															}
														}
													}
												}
											});
								}
							});

					btn1.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent arg0) 
								{
									JFileChooser fc = new JFileChooser(DIRNAME);
									fc.setDialogTitle("Edit Entries");

									fc.setFileFilter(new FileNameExtensionFilter("Entry","en","td","txt"));	
									fc.setApproveButtonText("Edit");
									fc.setApproveButtonToolTipText("Edit selected Entry");
									fc.setMultiSelectionEnabled(true);

									int returnVal = fc.showOpenDialog(frame);
									if(returnVal == JFileChooser.APPROVE_OPTION)
									{
										File f=fc.getSelectedFile();
										if(f.exists())
										{
											String fname = f.getName();
											String ext = fname.substring(fname.lastIndexOf('.')+1);
											//General Entry
											if(ext.equals("en"))
											{
												JFrame fram=new JFrame("EDIT_FRAM");
												JPanel panl=new JPanel();
												JTextField j1=new JTextField(15);

												JButton jb1=new JButton("Add");
												fram.setLayout(new FlowLayout());
												fram.add(panl);
												fram.add(j1);
												fram.add(jb1);
												fram.pack();

												fram.setVisible(true);

												jb1.addActionListener(new ActionListener() {

													@Override
													public void actionPerformed(ActionEvent e) 
													{
														try
														{
															FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
															String s=j1.getText().toString();
															fw.append(s);

															fw.close();
															JOptionPane.showMessageDialog(null, "Entry Edited", "InfoBox: " + "Entry", JOptionPane.INFORMATION_MESSAGE);

														} catch (IOException e1) {

															e1.printStackTrace();
														}
													}
												});
											}
											else if(ext.equals("txt"))
											{
												if(f.exists())
												{
													JFrame fm=new JFrame("Modify");
													JPanel pl=new JPanel();
													JTextField j1=new JTextField(15);

													JButton jb1=new JButton("Add");
													fm.setLayout(new FlowLayout());
													fm.add(pl);
													fm.add(j1);
													fm.add(jb1);
													fm.pack();

													fm.setVisible(true);

													jb1.addActionListener(new ActionListener() {

														@Override
														public void actionPerformed(ActionEvent e) {
															try
															{
																FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
																String s=j1.getText().toString();
																fw.append(s);

																fw.close();
																JOptionPane.showMessageDialog(null, "Entry Edited", "InfoBox: " + "Entry", JOptionPane.INFORMATION_MESSAGE);

															} catch (IOException e1) {

																e1.printStackTrace();
															}

														}

													});

												}
												else {
													JOptionPane.showMessageDialog(null, "File not Found", "InfoBox: " + "Create File", JOptionPane.INFORMATION_MESSAGE);
												}
											}

											else if(ext.equals("td"))
											{
												Scanner sc=new Scanner(System.in);
												JFrame Aframe=new JFrame("TO DO LIST");
												JTextField tf=new JTextField();
												JTextField tf1=new JTextField();
												JButton b1=new JButton("ADD TASK");
												JButton b2=new JButton("DISPLAY");
												JButton b3=new JButton("REMOVE TASK");
												b2.setBounds(100,100,300,20);
												b1.setBounds(50,50,300,20);
												tf.setBounds(75,75,100,20);
												tf1.setBounds(150,150,100,20);
												b3.setBounds(200,200,300,20);
												Aframe.add(tf1);
												Aframe.add(b3);
												Aframe.add(tf);
												Aframe.setLayout(null);
												Aframe.setSize(600,600);
												Aframe.setVisible(true);
												Aframe.add(b1);
												Aframe.add(b2);

												ArrayList<String> al=new ArrayList <String>();

												b1.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e){
														ArrayList<String> x=new ArrayList<String>();

														try{
															FileInputStream fis=new FileInputStream(f);
															ObjectInputStream ois=new ObjectInputStream(fis);
															x = (ArrayList) ois.readObject();
															ois.close();
															fis.close();
														}
														catch (IOException e1){
															e1.printStackTrace();
															return;
														}
														catch (ClassNotFoundException c){
															System.out.println("Class not found");
															c.printStackTrace();
															return;

														}

														String t=tf.getText();
														x.add(t);

														try{
															FileOutputStream fos=new FileOutputStream(f);
															ObjectOutputStream oos= new ObjectOutputStream(fos);
															oos.writeObject(x);
															oos.close();
															fos.close();
														}catch(Exception ex)
														{
															System.out.println(ex);
														}
													}
												});

												b2.addActionListener(new ActionListener(){
													public void actionPerformed(ActionEvent e7){
														ArrayList<String>  x=new ArrayList<String>();

														try{
															FileInputStream fis=new FileInputStream(f);
															ObjectInputStream ois=new ObjectInputStream(fis);
															x= (ArrayList) ois.readObject();
															ois.close();
															fis.close();
														}catch (IOException e1){
															e1.printStackTrace();
															return;
														} catch (ClassNotFoundException c){
															System.out.println("Class not found");
															c.printStackTrace();
															return;
														}

														for(String temp : x){
															System.out.println(temp);
														}

													}
												});

												b3.addActionListener(new ActionListener(){
													public void actionPerformed (ActionEvent e8){

														ArrayList<String> x=new ArrayList<String>();

														try{
															FileInputStream fis=new FileInputStream(f);
															ObjectInputStream ois=new ObjectInputStream(fis);
															x= (ArrayList) ois.readObject();
															ois.close();
															fis.close();
														}catch (IOException e1){
															e1.printStackTrace();
															return;
														} catch (ClassNotFoundException c){
															System.out.println("Class not found");
															c.printStackTrace();
															return;
														}

														for(String temp : x){
															System.out.println(temp);
														}

														System.out.println("Which Task would you like to remove");
														int n=sc.nextInt();

														x.remove(n);

														try{
															FileOutputStream fos=new FileOutputStream(f);
															ObjectOutputStream oos= new ObjectOutputStream(fos);
															oos.writeObject(x);
															oos.close();
															fos.close();
														}catch(Exception ex)
														{
															System.out.println(ex);
														}
													}
												});
											}
										}
										else 
										{
											JOptionPane.showMessageDialog(null, "File not Found", "InfoBox: " + "Create File", JOptionPane.INFORMATION_MESSAGE);
										}
									}
								}
							});

					btn2.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent e) 
								{
									JFileChooser fc = new JFileChooser(DIRNAME);
									fc.setDialogTitle("Deleting Entries");

									fc.setFileFilter(new FileNameExtensionFilter("Entry","en","td","txt"));	
									fc.setApproveButtonText("Delete");
									fc.setApproveButtonToolTipText("Delete selected Entry/Entries"); 	 	

									fc.setMultiSelectionEnabled(true);

									int returnVal = fc.showOpenDialog(frame);
									if(returnVal == JFileChooser.APPROVE_OPTION)
									{
										File f[] = fc.getSelectedFiles();
										for(int i = 0; i < f.length; i++)
											f[i].delete();
									}
								}
							});

					btn3.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent arg0) 
								{
									JFileChooser fc = new JFileChooser(DIRNAME);
									fc.setDialogTitle("Display Files");

									fc.setFileFilter(new FileNameExtensionFilter("Entry","en","td","txt"));	
									fc.setApproveButtonText("Display Entry Details");
									fc.setApproveButtonToolTipText("Display details of the selected entry");

									fc.setMultiSelectionEnabled(false);

									int returnVal = fc.showOpenDialog(frame);
									if(returnVal == JFileChooser.APPROVE_OPTION)
									{

										File f = fc.getSelectedFile();
										String fname = f.getName();
										String ext = fname.substring(fname.lastIndexOf('.')+1);
										//General Entry
										if(ext.equals("en"))
										{
											try{
												String s=null;
												FileReader f1=new FileReader(f);

												JFrame fra=new JFrame();
												JPanel p1=new JPanel();
												JLabel lab=new JLabel();
												p1.add(lab);
												fra.add(p1);
												f1.close();
												fra.setVisible(true);
												fra.setSize(300,300);
												BufferedReader br = new BufferedReader(new FileReader(f));
												String line = null;
												while ((line = br.readLine()) != null) {
													s=line;
												}
												lab.setText(s);

											}
											catch(IOException e)
											{
												System.out.println(e.getMessage());
											}
										}
										else if(ext.equals("txt"))
										{
											try{
												String s=null;
												FileReader f1=new FileReader(f);

												JFrame fdi=new JFrame();
												JPanel p1=new JPanel();
												JLabel lab=new JLabel();
												p1.add(lab);
												fdi.add(p1);
												f1.close();
												fdi.setVisible(true);
												fdi.setSize(300,300);
												BufferedReader br = new BufferedReader(new FileReader(f));
												String line = null;
												while ((line = br.readLine()) != null) {
													s=line;
												}
												lab.setText(s);

											}
											catch(IOException e)
											{
												System.out.println(e.getMessage());
											}

										}
										else if(ext.equals("td"))
										{
											Scanner sc=new Scanner(System.in);

											JFrame Aframe=new JFrame("TO DO LIST");
											JTextField tf=new JTextField();
											JTextField tf1=new JTextField();
											JButton b1=new JButton("ADD TASK");
											JButton b2=new JButton("DISPLAY");
											JButton b3=new JButton("REMOVE TASK");
											b2.setBounds(100,100,300,20);
											b1.setBounds(50,50,300,20);
											tf.setBounds(75,75,100,20);
											tf1.setBounds(150,150,100,20);
											b3.setBounds(200,200,300,20);
											Aframe.add(tf1);
											Aframe.add(b3);
											Aframe.add(tf);
											Aframe.setLayout(null);
											Aframe.setSize(600,600);
											Aframe.setVisible(true);
											Aframe.add(b1);
											Aframe.add(b2);

											ArrayList<String> al=new ArrayList <String>();
											b1.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e){
													ArrayList<String> x=new ArrayList<String>();

													try{
														FileInputStream fis=new FileInputStream(f);
														ObjectInputStream ois=new ObjectInputStream(fis);
														x = (ArrayList) ois.readObject();
														ois.close();
														fis.close();
													}
													catch (IOException e1){
														e1.printStackTrace();
														return;
													}
													catch (ClassNotFoundException c){
														System.out.println("Class not found");
														c.printStackTrace();
														return;

													}

													String t=tf.getText();
													x.add(t);

													try{
														FileOutputStream fos=new FileOutputStream(f);
														ObjectOutputStream oos= new ObjectOutputStream(fos);
														oos.writeObject(x);
														oos.close();
														fos.close();
													}catch(Exception ex)
													{
														System.out.println(ex);
													}
												}
											});

											b2.addActionListener(new ActionListener(){
												public void actionPerformed(ActionEvent e7){
													ArrayList<String>  x=new ArrayList<String>();

													try{
														FileInputStream fis=new FileInputStream(f);
														ObjectInputStream ois=new ObjectInputStream(fis);
														x= (ArrayList) ois.readObject();
														ois.close();
														fis.close();
													}catch (IOException e1){
														e1.printStackTrace();
														return;
													} catch (ClassNotFoundException c){
														System.out.println("Class not found");
														c.printStackTrace();
														return;
													}

													for(String temp : x){
														System.out.println(temp);
													}

												}
											});

											b3.addActionListener(new ActionListener(){
												public void actionPerformed (ActionEvent e8){

													ArrayList<String> x=new ArrayList<String>();

													try{
														FileInputStream fis=new FileInputStream(f);
														ObjectInputStream ois=new ObjectInputStream(fis);
														x= (ArrayList) ois.readObject();
														ois.close();
														fis.close();
													}catch (IOException e1){
														e1.printStackTrace();
														return;
													} catch (ClassNotFoundException c){
														System.out.println("Class not found");
														c.printStackTrace();
														return;
													}

													for(String temp : x){
														System.out.println(temp);
													}

													System.out.println("Which Task would you like to remove");
													int n=sc.nextInt();

													x.remove(n);
													try{
														FileOutputStream fos=new FileOutputStream(f);
														ObjectOutputStream oos= new ObjectOutputStream(fos);
														oos.writeObject(x);
														oos.close();
														fos.close();
													}catch(Exception ex)
													{
														System.out.println(ex);
													}
												}
											});
										}
									}
								}
							});
				}
			}
		});

		tabbedPane.addTab("GoTo",null,goToMenu,"Go To Notebook");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);	

		//Display, Add, Delete a Notebook

		JPanel manageNb = new JPanel();
		JButton createNb = new JButton("Create");
		JButton deleteNb = new JButton("Delete");
		JButton displayNb = new JButton("Display");

		//Display, Add, Delete an Entry

		JPanel manageEn = new JPanel();
		JButton createEn = new JButton("Create");
		JButton deleteEn = new JButton("Delete");
		JButton displayEn = new JButton("Display");

		//ADDING A NEW NOTEBOOK

		createNb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				String s = JOptionPane.showInputDialog(frame, "Enter Notebook Name", "Creating Notebook", JOptionPane.PLAIN_MESSAGE);
				if(s!=null)
				{

					//Creating file
					File f = new File(s);

					if(!f.exists())
					{
						//Creating Notebook Directory
						try
						{
							f.mkdir();
							infoBox(frame, "Notebook "+s+" was successfully created!", "Creation Successful");
						}
						catch(SecurityException e)
						{
							System.out.println(e.getMessage());
						}
					}
					else
					{
						infoBox(frame, "Notebook "+s+" already exists.", "Error");
					}
				}
			}
		});

		//DELETING NOTEBOOKS

		deleteNb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Deleting Notebooks");

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setApproveButtonText("Delete");
				fc.setApproveButtonToolTipText("Delete selected notebook"); 	 	

				fc.setMultiSelectionEnabled(false);

				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File dir = fc.getSelectedFile();

					//List out all the files in the directory and deletes them
					File[] contents = dir.listFiles();
					if(contents!=null)
					{
						for(File f : contents)
						{
							f.delete();
						}
					}
					//Deletes empty directory
					dir.delete();
					//Deletion Successful Message
					infoBox(frame, "The selected Notebook is successfully deleted.", "Deletion Successful");
				}
			}
		});

		//DISPLAYING NOTEBOOKS

		displayNb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Display Files");

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setApproveButtonText("Display Notebook Details");
				fc.setApproveButtonToolTipText("Display details of the selected notebook");

				fc.setMultiSelectionEnabled(false);

				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{

					//Getting name of directory
					File dir = fc.getSelectedFile();
					try{
						File[] contents = dir.listFiles();
						String[] entries = new String[contents.length];
						for(int i = 0; i < contents.length; i++)
							entries[i] = contents[i].getName();

						//Setting all strings holding Notebook details
						String nameHTML = "<html>Title: <strong>" + dir.getName()+"</strong></html>";
						String entryTitle = "<html>Entries:</html>";
						String entriesHTML="<html><ul>";
						for(int i = 0; i < entries.length; i++)
							entriesHTML+="<li>"+entries[i]+"</li>";
						entriesHTML +="</ul></li></html>";
						if(entries.length==0)
							entriesHTML = "<html>No entries available.</html>";

						//Creating all JLabels to hold the Notebook Details

						JLabel nbName  = new JLabel(nameHTML);
						JLabel nbEntryTitle = new JLabel(entryTitle);
						JLabel nbEntries  = new JLabel(entriesHTML);

						//Setting alignments for all JLabels
						nbName.setAlignmentX(Component.RIGHT_ALIGNMENT);
						nbEntryTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
						nbEntries.setAlignmentX(Component.RIGHT_ALIGNMENT);

						//Adding all JLabels to a JPanel
						JPanel wrapper = new JPanel();
						wrapper.add(nbName);
						wrapper.add(Box.createRigidArea(new Dimension(200,10)));
						wrapper.add(nbEntryTitle);
						wrapper.add(nbEntries);

						//Setting preferred size and adding borders
						wrapper.setPreferredSize(new Dimension(500,200));
						TitledBorder b = BorderFactory.createTitledBorder("Notebook Details");
						b.setTitleJustification(TitledBorder.CENTER);

						wrapper.setBorder(b);
						wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));


						JDialog disDialog = new JDialog(frame,"Display");
						disDialog.setLayout(new FlowLayout());

						disDialog.add(wrapper);
						disDialog.pack();
						disDialog.setVisible(true);

					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
			}
		});

		//Adding MANAGE NOTEBOOK LAYOUT
		JLabel createLb = new JLabel("Creating a new notebook");
		JLabel deleteLb = new JLabel("Deleting a notebook or multiple notebooks");
		JLabel displayLb = new JLabel("Displaying details of a selected notebook");

		//Setting the font sizes

		createLb.setFont(largerFont);
		createNb.setFont(largerFont);

		deleteLb.setFont(largerFont);
		deleteNb.setFont(largerFont);

		displayLb.setFont(largerFont);
		displayNb.setFont(largerFont);

		createLb.setBorder(new EmptyBorder(0,indent,0,0));
		deleteLb.setBorder(new EmptyBorder(0,indent,0,0));
		displayLb.setBorder(new EmptyBorder(0,indent,0,0));

		JPanel createJp = new JPanel();
		JPanel deleteJp = new JPanel();
		JPanel displayJp = new JPanel();

		createJp.setLayout(new BorderLayout());
		createJp.add(createLb,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.add(createNb,gbc);
		x.setBorder(new EmptyBorder(0,0,0,indent));
		createJp.add(x, BorderLayout.LINE_END);
		createJp.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		createJp.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Create");
		createJp.setBorder(b);

		deleteJp.setLayout(new BorderLayout());
		deleteJp.add(deleteLb,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.setBorder(new EmptyBorder(0,0,0,indent));

		x.add(deleteNb,gbc);
		deleteJp.add(x, BorderLayout.LINE_END);
		deleteJp.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		deleteJp.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Delete");
		deleteJp.setBorder(b);


		displayJp.setLayout(new BorderLayout());
		displayJp.add(displayLb,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.add(displayNb,gbc);
		x.setBorder(new EmptyBorder(0,0,0,indent));
		displayJp.add(x, BorderLayout.LINE_END);
		displayJp.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		displayJp.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Display");
		displayJp.setBorder(b);

		manageNb.add(createJp);
		manageNb.add(Box.createVerticalGlue());
		manageNb.add(deleteJp);
		manageNb.add(displayJp);

		manageNb.setLayout(new BoxLayout(manageNb, BoxLayout.Y_AXIS));

		tabbedPane.addTab("Manage Notebooks",null,manageNb,"Create, Delete and Display Notebooks");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);	
		//Add, Edit, Remove, Filter Entries
		//ADDING ENTRY

		createEn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				//TODO Creating Entries get ToDoList and GeneralEntry
				//TODO Probably do dialog which obtains the type so you can call functons
			}
		});

		//DELETING ENTRY

		deleteEn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Deleting Entries");

				//TODO Mutiple File Names

				fc.setFileFilter(new FileNameExtensionFilter("Entry","td","en"));	
				fc.setApproveButtonText("Delete");
				fc.setApproveButtonToolTipText("Delete selected entry/entries"); 	 	

				fc.setMultiSelectionEnabled(true);

				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File f[] = fc.getSelectedFiles();
					for(int i = 0; i < f.length; i++)
						f[i].delete();
					infoBox(frame, "The selected Entries were successfully deleted.", "Deletion Successful");
				}
			}
		});

		//DISPLAYING ENTRIES

		displayEn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Display Files");

				fc.setFileFilter(new FileNameExtensionFilter("Entry","en","td"));	
				fc.setApproveButtonText("Display Entry Details");
				fc.setApproveButtonToolTipText("Display details of the selected entry");

				fc.setMultiSelectionEnabled(false);

				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
			}
			}
		});

		//Adding MANAGE ENTRY LAYOUT
		JLabel createLbE = new JLabel("Creating a new entry");
		JLabel deleteLbE = new JLabel("Deleting an entry or multiple entries");
		JLabel displayLbE = new JLabel("Displaying details of a selected entry");

		//Setting the font sizes

		createLbE.setFont(largerFont);
		createEn.setFont(largerFont);

		deleteLbE.setFont(largerFont);
		deleteEn.setFont(largerFont);

		displayLbE.setFont(largerFont);
		displayEn.setFont(largerFont);

		createLbE.setBorder(new EmptyBorder(0,indent,0,0));
		deleteLbE.setBorder(new EmptyBorder(0,indent,0,0));
		displayLbE.setBorder(new EmptyBorder(0,indent,0,0));




		JPanel createJpE = new JPanel();
		JPanel deleteJpE = new JPanel();
		JPanel displayJpE = new JPanel();

		createJpE.setLayout(new BorderLayout());
		//		x = new JPanel();
		//		x.add(createLb);
		createJpE.add(createLbE,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.add(createEn,gbc);
		x.setBorder(new EmptyBorder(0,0,0,indent));
		createJpE.add(x, BorderLayout.LINE_END);
		createJpE.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		createJpE.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Create");
		createJpE.setBorder(b);

		deleteJpE.setLayout(new BorderLayout());
		//		x = new JPanel();
		//		x.add(createLb);
		deleteJpE.add(deleteLbE,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.setBorder(new EmptyBorder(0,0,0,indent));

		x.add(deleteEn,gbc);
		deleteJpE.add(x, BorderLayout.LINE_END);
		deleteJpE.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		deleteJpE.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Delete");
		deleteJpE.setBorder(b);


		displayJpE.setLayout(new BorderLayout());
		//		x = new JPanel();
		//		x.add(createLb);
		displayJpE.add(displayLbE,BorderLayout.LINE_START);

		x = new JPanel(new GridBagLayout());
		x.add(displayEn,gbc);
		x.setBorder(new EmptyBorder(0,0,0,indent));
		displayJpE.add(x, BorderLayout.LINE_END);
		displayJpE.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.PAGE_END);

		displayJpE.setPreferredSize(new Dimension(600,65));
		b = BorderFactory.createTitledBorder("Display");
		displayJpE.setBorder(b);

		manageEn.add(createJpE);
		manageEn.add(Box.createVerticalGlue());
		manageEn.add(deleteJpE);
		manageEn.add(displayJpE);

		manageEn.setLayout(new BoxLayout(manageEn, BoxLayout.Y_AXIS));
	//Frame add and all
		frame.add(tabbedPane);
		frame.pack();
		frame.setVisible(true);
	}

	// Message box
	public static void infoBox(JFrame f, String message, String titlebar)
	{
		JOptionPane.showMessageDialog(f, message, titlebar, JOptionPane.INFORMATION_MESSAGE);
	}

}
