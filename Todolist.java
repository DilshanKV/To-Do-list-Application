package login;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import codes.dbconnect;

import java.sql.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Todolist {

	JFrame frame;
	
	Connection conn = null;
	java.sql.Statement stmt = null;
	
	private JTextField id_box;
	private JTable table;
	private JTextField search_textbox;
	String mainSql = "SELECT * FROM todolist ORDER BY taskNo";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Todolist window = new Todolist();
					window.frame.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Todolist() {
		initialize();
		try {
			conn = dbconnect.connect();
			showtable(mainSql);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	

	public void showtable(String sql) {
		try {
			stmt = (Statement) conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i = 0;i<cols;i++) {
				colName[i] = rsmd.getColumnName(i+1);
			}
			model.setColumnIdentifiers(colName);
			
			String task_no,task,due_date,status;
			
			while(rs.next()) {
				task_no = rs.getString(1);
				task = rs.getString(2);
				due_date = rs.getString(3);
				status = rs.getString(4);
				String[] row = {task_no,task,due_date,status};
				model.addRow(row);
			}
				
		} catch (SQLException er) {
			JOptionPane.showMessageDialog(null,"Something wrong !\n(imp : \\ is an illegale character)");
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBackground(new Color(0, 0, 0));
		frame.setBounds(400, 100, 833, 631);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("TO DO LIST");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Sitka Text", Font.BOLD, 50));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(188, 26, 437, 70);
		frame.getContentPane().add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 95, 824, 259);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.setRowHeight(20);
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		scrollPane_1.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 352, 606, 259);
		panel.setBackground(new Color(255, 255, 128));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		id_box = new JTextField();
		id_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		id_box.setBounds(134, 10, 459, 44);
		panel.add(id_box);
		id_box.setColumns(10);
		
		final JComboBox year_box = new JComboBox();
		year_box.setToolTipText("Select a year");
		year_box.setBounds(134, 159, 87, 31);
		panel.add(year_box);
		year_box.setModel(new DefaultComboBoxModel(new String[] {"2022", "2023", "2024", "2025"}));
		year_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		final JComboBox month_box = new JComboBox();
		month_box.setToolTipText("Select a month");
		month_box.setBounds(231, 159, 87, 31);
		panel.add(month_box);
		month_box.setModel(new DefaultComboBoxModel(new String[] {"", "Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"}));
		month_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		final JComboBox day_box = new JComboBox();
		day_box.setToolTipText("Select a day");
		day_box.setBounds(328, 159, 74, 31);
		panel.add(day_box);
		day_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		day_box.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		
		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setBounds(21, 193, 157, 44);
		panel.add(lblStatus);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		final JComboBox status_box = new JComboBox();
		status_box.setBounds(134, 200, 184, 31);
		panel.add(status_box);
		status_box.setModel(new DefaultComboBoxModel(new String[] {"Not Started", "Working On", "Hold", "Done"}));
		status_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblDueDate = new JLabel("DUE DATE");
		lblDueDate.setBounds(21, 152, 157, 44);
		panel.add(lblDueDate);
		lblDueDate.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblTask = new JLabel("TASK ");
		lblTask.setBounds(21, 69, 157, 44);
		panel.add(lblTask);
		lblTask.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblNewLabel = new JLabel("TASK NO");
		lblNewLabel.setBounds(21, 10, 157, 44);
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(new Color(128, 64, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		final JTextPane task_box = new JTextPane();
		task_box.setFont(new Font("Tahoma", Font.BOLD, 20));
		task_box.setBounds(134, 64, 459, 85);
		panel.add(task_box);
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 0));
		panel_2.setBounds(597, 358, 227, 242);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(22, 10, 187, 63);
		panel_2.add(panel_1);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "SEARCH", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setLayout(null);
		
		search_textbox = new JTextField();
		search_textbox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				
				String searchid = search_textbox.getText();
				String sqlsearch = "SELECT * FROM todolist WHERE taskNo LIKE '"+searchid+"' OR task LIKE '%"+searchid+"%' OR dueDate LIKE '%"+searchid+"%' OR status LIKE '%"+searchid+"%' ORDER BY taskNo";
				table.setModel(new DefaultTableModel());
				showtable(sqlsearch);
				
				String sqlUpdate = "SELECT * FROM todolist WHERE taskNo = '"+searchid+"' ";
				try {
					stmt = conn.createStatement();
					ResultSet rs = (ResultSet) stmt.executeQuery(sqlUpdate);
					
					if (rs.next()) {
						id_box.setText(rs.getString("taskNo"));
						task_box.setText(rs.getString("task"));
					} else {
						id_box.setText("");
						task_box.setText("");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		search_textbox.setBounds(10, 15, 167, 38);
		panel_1.add(search_textbox);
		search_textbox.setFont(new Font("Tahoma", Font.BOLD, 20));
		search_textbox.setColumns(10);
		
		JButton update_btn = new JButton("UPDATE");
		update_btn.setFocusable(false);
		update_btn.setForeground(new Color(255, 255, 255));
		update_btn.setBackground(new Color(0, 255, 128));
		update_btn.setToolTipText("Make sure to update the due date and the status");
		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String taskNo,task,year,month,day,status;
				
				taskNo = id_box.getText();
				task = task_box.getText();
				year = year_box.getSelectedItem().toString();
				month = month_box.getSelectedItem().toString();
				day = day_box.getSelectedItem().toString();
				status = status_box.getSelectedItem().toString();
				
				String sql = "SELECT * FROM todolist WHERE taskNo = '"+taskNo+"'";
				
				try {
					stmt = conn.createStatement();
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					
					if (rs.next()) {
						if (taskNo.trim().length() != 0 & task.trim().length() != 0) {
							String sql1 = "UPDATE todolist SET task = '"+task+"',dueDate = '"+year+"-"+month+"-"+day+"',status = '"+status+"' WHERE taskNo = '"+taskNo+"'";
							stmt = conn.createStatement();
							stmt.executeUpdate(sql1);
							JOptionPane.showMessageDialog(null, "successfully updated !");
							id_box.setText("");
							task_box.setText("");
							id_box.requestFocus();
							table.setModel(new DefaultTableModel());				//remove the table (default mood)
							showtable(mainSql);											//show table
						} else if (taskNo.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "You need to type a task number !");
						} else {
							JOptionPane.showMessageDialog(null, "You need to type the task !");
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "This task number is not in the dataset !");	
					}  
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"Somthing wrong !");
				}
			}
		});
		update_btn.setBounds(22, 137, 187, 44);
		panel_2.add(update_btn);
		update_btn.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton delete_btn = new JButton("DELETE");
		delete_btn.setFocusable(false);
		delete_btn.setForeground(new Color(255, 255, 255));
		delete_btn.setBackground(new Color(253, 57, 0));

		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchid = search_textbox.getText();
	
				try {
					if (searchid.trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "You need to enter the task number");
					} else {
						int searchidn = Integer.parseInt(searchid);
						String sql = "DELETE FROM todolist WHERE taskNo='"+searchidn+"'";
						stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, "Successfully deleted from the database");
						search_textbox.setText("");
						table.setModel(new DefaultTableModel());				//remove the table (default mood)
						showtable(mainSql);
						id_box.setText("");
						task_box.setText("");
						id_box.requestFocus();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "You need to searh from id or\n this id is not in the data base");
					search_textbox.setText("");
					table.setModel(new DefaultTableModel());				//remove the table (default mood)
					showtable(mainSql);
				}
				
			}
		});
		delete_btn.setBounds(22, 188, 187, 44);
		panel_2.add(delete_btn);
		delete_btn.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 128, 255));
		btnNewButton.setBounds(20, 83, 189, 44);
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String taskNo,task,year,month,day,status;
				
				taskNo = id_box.getText();
				task = task_box.getText();
				year = year_box.getSelectedItem().toString();
				month = month_box.getSelectedItem().toString();
				day = day_box.getSelectedItem().toString();
				status = status_box.getSelectedItem().toString();
				
				String sql = "SELECT * FROM todolist WHERE taskNo = '"+taskNo+"'";
				
				try {
					stmt = conn.createStatement();
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "You can not add this task number !");
					} else {
						if (taskNo.trim().length() != 0 & task.trim().length() != 0) {
							String sql1 = "INSERT INTO todolist(taskNo,task,dueDate,status) VALUES ('" + taskNo+ "','" + task+ "','"+year+"-"+month+"-"+day+"','"+status+"') ";
							stmt = conn.createStatement();
							stmt.executeUpdate(sql1);
							JOptionPane.showMessageDialog(null, "successfully added to the database !");
							id_box.setText("");
							task_box.setText("");
							id_box.requestFocus();
							table.setModel(new DefaultTableModel());				//remove the table (default mood)
							showtable(mainSql);											//show table
						} else if (taskNo.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "You need to type a task number !");
						} else {
							JOptionPane.showMessageDialog(null, "You need to type the task !");
						}
						
						
					}  
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"Somthing wrong !");
				}
			}
			
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnNewButton_1 = new JButton("HOME");
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow mainwindow = new MainWindow();
				mainwindow.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(10, 10, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
	}

}
