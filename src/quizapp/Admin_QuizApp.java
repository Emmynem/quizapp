package quizapp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Emmynem
 */
public class Admin_QuizApp {
    
    // Declaring all Components to be used
    
    // All Frames
    JFrame MainFrame;
    
    // All Panels
    JPanel Layer_jpa, Home_jpa, Login_jpa, Register_jpa, Dashboard_jpa, Question_jpa, 
    AllQuiz_jpa, Dash_edit_profile_jpa, Edit_Question_jpa;
    
    // Home Labels
    JLabel home_lbl, welcome_lbl, email_lbl, password_lbl, login_lbl, 
    or_lbl, register_lbl, fname_lbl, lname_lbl, confirm_password_lbl;
    
    // Home TextFields
    JTextField log_email_txt, fname_txt, lname_txt, reg_email_txt;
    
    // Home Password Fields
    JPasswordField log_password_jpf, reg_password_jpf, confirm_password_jpf;
    
    // Home Buttons
    JButton login_jbtn, register_jbtn, goto_login_jbtn, goto_register_jbtn;
    
    // Dashboard Labels
    JLabel dashboard_lbl, dash_welcome_lbl, add_quiz_lbl, all_quiz_lbl, edit_profile_lbl,
    dash_fname_lbl, dash_lname_lbl, dash_confirm_password_lbl, dash_email_lbl, dash_password_lbl,
    dash_old_password_lbl;
    
    // Dashboard TextFields
    JTextField dash_email_txt, dash_fname_txt, dash_lname_txt;
    
    // Dashboard Password Fields
    JPasswordField dash_password_jpf, dash_old_password_jpf, dash_confirm_password_jpf;
    
    // Dashboard Buttons
    JButton logout_jbtn, dash_edit_profile_jbtn, dash_save_changes_jbtn, add_quiz_jbtn, all_quiz_jbtn;
    
    // Question Labels
    JLabel question_lbl, add_question_lbl, new_question_lbl, option_1_lbl, option_2_lbl, option_3_lbl,
    option_4_lbl, answer_lbl;
    
    // Question TextAreas
    JTextArea new_question_jta, option_1_jta, option_2_jta, option_3_jta, option_4_jta;
    
    // Question ComboBoxes
    JComboBox<String> answer_jcb;
    
    // Question ScrollPanes
    JScrollPane new_question_scroll, option_1_scroll, option_2_scroll, option_3_scroll, option_4_scroll;
    
    // Question Buttons
    JButton question_back_to_dashboard_jbtn, save_question_jbtn;
    
    // Edit Question Labels
    JLabel edit_question_lbl, edit_add_question_lbl, edit_new_question_lbl, edit_option_1_lbl, edit_option_2_lbl, edit_option_3_lbl,
    edit_option_4_lbl, edit_answer_lbl;
    
    // Edit TextAreas
    JTextArea edit_new_question_jta, edit_option_1_jta, edit_option_2_jta, edit_option_3_jta, edit_option_4_jta;
    
    // Edit ComboBoxes
    JComboBox<String> edit_answer_jcb;
    
    // Edit ScrollPanes
    JScrollPane edit_new_question_scroll, edit_option_1_scroll, edit_option_2_scroll, edit_option_3_scroll, edit_option_4_scroll;
    
    // Edit Buttons
    JButton edit_question_back_to_dashboard_jbtn, edit_save_question_jbtn;
    
    // AllQuiz Labels
    JLabel allQuiz_lbl;
    
    // AllQuiz TextFields
    
    // AllQuiz ScrollPanes
    JScrollPane allQuiz_scroll;
    
    // AllQuiz Right Click PopUpMenu
    JPopupMenu right_click_jmu;
    
    // AllQuiz Right Click PopUpMenu Items
    JMenuItem right_click_edit_jmi, right_click_delete_jmi;
    
    // AllQuiz Table
    JTable allQuiz_tbl;
    
    // AllQuiz DTM
    DefaultTableModel dtm_allQuiz;
    
    // AllQuiz objects
    Object [] allQuiz_obj;
    
    // AllQuiz Buttons
    JButton allQuiz_back_to_dashboard_jbtn, allQuiz_refresh_jbtn;
    
    // Menu bar
    JMenuBar menubar_jmb;
    // Menus
    JMenu connection_jmu;
    // MenuItem
    JMenuItem refresh_jmi;
    
    // Used to keep track of the database connection
    private boolean connection = false;
    
    // Used to check if there are any admin currently in database
    private String admin = "";
    
    // SQL Components
    Connection sql_con;
    Statement sql_stmt;
    PreparedStatement sql_stat; 
    ResultSet rs;
    
    private String global_edit_question_id;
    private String logged_in_id;
    private String logged_in_fname;
    private String logged_in_lname;
    private String logged_in_email;
    private String logged_in_password;
    
    public Admin_QuizApp(){
        // Initializing the MainFrame
        MainFrame = new JFrame();
        
        // Creating the MenuBar
        menubar_jmb = new JMenuBar();
        
        // Setting the MenuBar to the MainFrame
        MainFrame.setJMenuBar(menubar_jmb);
        
        // Adding New Menu (Connection)
        connection_jmu = new JMenu("Connection");
        connection_jmu.setMnemonic('c');
        
        // Adding Connection Menu to the MenuBar
        menubar_jmb.add(connection_jmu);
        
        // Adding Connection Menu Items
        refresh_jmi = new JMenuItem("Connect / Refresh");
        connection_jmu.add(refresh_jmi);
        
        refresh_jmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        
        
        // Initializing the Layer
        Layer_jpa = new JPanel();
        Layer_jpa.setLayout(null);
        
    // ------------------------------------------------------------------------- 
        // Initializing the Home Panel
        Home_jpa = new JPanel();
        Home_jpa.setBackground(new java.awt.Color(0, 51, 102));
        Home_jpa.setBounds(0, 0, 1100, 700);
        
        // Home - Labels
        welcome_lbl = new JLabel();
        welcome_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quizapp/images/Network 2.PNG")));
        
        // Home - Buttons
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);
        
        // Home - Sub - Panels
    // -----------------------------------------
        Login_jpa = new JPanel();
        Login_jpa.setBackground(new java.awt.Color(0, 51, 102));
        
        // Login - Labels
        email_lbl = new JLabel("Email");
        email_lbl.setForeground(Color.WHITE);
        email_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        password_lbl = new JLabel("Password");
        password_lbl.setForeground(Color.WHITE);
        password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        or_lbl = new JLabel("or");
        or_lbl.setForeground(Color.white);
        or_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Login - Buttons
        login_jbtn = new JButton("Login");
        login_jbtn.setCursor(cur);
        login_jbtn.setBackground(Color.green);
        login_jbtn.setForeground(Color.BLACK);
        login_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        login_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        login_jbtn.setEnabled(connection);
        
        goto_register_jbtn = new JButton("Register");
        goto_register_jbtn.setCursor(cur);
        goto_register_jbtn.setBackground(Color.blue);
        goto_register_jbtn.setForeground(Color.WHITE);
        goto_register_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        goto_register_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                go_to_registerButtonActionPerformed(evt);
            }
        });
        goto_register_jbtn.setEnabled(connection);
        
        // Login - TextFields
        log_email_txt = new JTextField(15);
        log_email_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Login - PasswordFields
        log_password_jpf = new JPasswordField(15);
        log_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        log_password_jpf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                Character c = e.getKeyChar();
                if(c == KeyEvent.VK_ENTER){
                    loginAdmin();
                }
            }
        });
        
        // Login Layout
        Login_jpa.setLayout(null);
        
        // Login Panels - attr
        Login_jpa.setBounds(630, 160, 400, 300);
        
        // Login Default Label
        login_lbl = new JLabel("Admin - Login");
        
        // Login Default Label - attr
        login_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        login_lbl.setForeground(Color.WHITE);
        
        // Login components - attr
        login_lbl.setBounds(10, 0, 300, 40);
        
        // Login Labels - attr
        email_lbl.setBounds(10, 50, 100, 30);
        password_lbl.setBounds(10, 120, 100, 30);
        
        // Login TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        log_email_txt.setBounds(10, 80, 380, 30);
        log_password_jpf.setBounds(10, 150, 380, 30);
        login_jbtn.setBounds(80, 220, 100, 35);
        or_lbl.setBounds(190, 220, 30, 35);
        goto_register_jbtn.setBounds(220, 220, 100, 35);
        
        // Adding all Login Panel variables
        Login_jpa.add(login_lbl);
        Login_jpa.add(email_lbl);
        Login_jpa.add(log_email_txt);
        Login_jpa.add(password_lbl);
        Login_jpa.add(log_password_jpf);
        Login_jpa.add(login_jbtn);
        Login_jpa.add(or_lbl);
        Login_jpa.add(goto_register_jbtn);
        
        // Login Layout - attr
        Login_jpa.setLayout(null);
        
    // -----------------------------------------
        Register_jpa = new JPanel();
        Register_jpa.setBackground(new java.awt.Color(0, 51, 102));
        
        // Register - Labels
        fname_lbl = new JLabel("First Name");
        fname_lbl.setForeground(Color.WHITE);
        fname_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        lname_lbl = new JLabel("Last Name");
        lname_lbl.setForeground(Color.WHITE);
        lname_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        email_lbl = new JLabel("Email");
        email_lbl.setForeground(Color.WHITE);
        email_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        password_lbl = new JLabel("Password");
        password_lbl.setForeground(Color.WHITE);
        password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        confirm_password_lbl = new JLabel("Confirm Password");
        confirm_password_lbl.setForeground(Color.WHITE);
        confirm_password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        or_lbl = new JLabel("or");
        or_lbl.setForeground(Color.white);
        or_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Register - Buttons
        register_jbtn = new JButton("Register");
        register_jbtn.setCursor(cur);
        register_jbtn.setBackground(Color.green);
        register_jbtn.setForeground(Color.BLACK);
        register_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        register_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        register_jbtn.setEnabled(connection);
        
        goto_login_jbtn = new JButton("Login");
        goto_login_jbtn.setCursor(cur);
        goto_login_jbtn.setBackground(Color.blue);
        goto_login_jbtn.setForeground(Color.WHITE);
        goto_login_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        goto_login_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                go_to_loginButtonActionPerformed(evt);
            }
        });
        goto_login_jbtn.setEnabled(connection);
        
        // Register - TextFields
        fname_txt = new JTextField(15);
        fname_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        lname_txt = new JTextField(15);
        lname_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        reg_email_txt = new JTextField(15);
        reg_email_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Register - PasswordFields
        reg_password_jpf = new JPasswordField(15);
        reg_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        confirm_password_jpf = new JPasswordField(15);
        confirm_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Register Layout
        Register_jpa.setLayout(null);
        
        // Register Panels - attr
        Register_jpa.setBounds(630, 160, 400, 300);
        
        // Register Default Label
        register_lbl = new JLabel("Admin - Register");
        
        // Register Default Label - attr
        register_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        register_lbl.setForeground(Color.WHITE);
        
        // Register components - attr
        register_lbl.setBounds(10, 0, 300, 40);
        
        // Register Labels - attr
        fname_lbl.setBounds(10, 50, 100, 30);
        lname_lbl.setBounds(220, 50, 100, 30);
        email_lbl.setBounds(10, 110, 100, 30);
        password_lbl.setBounds(10, 170, 100, 30);
        confirm_password_lbl.setBounds(220, 170, 200, 30);
        
        // Register TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        fname_txt.setBounds(10, 80, 160, 30);
        lname_txt.setBounds(220, 80, 170, 30);
        reg_email_txt.setBounds(10, 140, 380, 30);
        reg_password_jpf.setBounds(10, 210, 160, 30);
        confirm_password_jpf.setBounds(220, 210, 170, 30);
        register_jbtn.setBounds(70, 250, 100, 35);
        or_lbl.setBounds(185, 250, 30, 35);
        goto_login_jbtn.setBounds(220, 250, 100, 35);
        
        // Adding all Register Panel variables
        Register_jpa.add(register_lbl);
        Register_jpa.add(fname_lbl);
        Register_jpa.add(fname_txt);
        Register_jpa.add(lname_lbl);
        Register_jpa.add(lname_txt);
        Register_jpa.add(email_lbl);
        Register_jpa.add(reg_email_txt);
        Register_jpa.add(password_lbl);
        Register_jpa.add(reg_password_jpf);
        Register_jpa.add(confirm_password_lbl);
        Register_jpa.add(confirm_password_jpf);
        Register_jpa.add(register_jbtn);
        Register_jpa.add(or_lbl);
        Register_jpa.add(goto_login_jbtn);
        
        // Register Layout - attr
        Register_jpa.setLayout(null);
        
    // -----------------------------------------
        
        // Home Layout
        Home_jpa.setLayout(null);
        
        // Home Panels - attr
        
        // Home Default Label
        home_lbl = new JLabel("Admin - Home");
        
        // Home Default Label - attr
        home_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        home_lbl.setForeground(Color.WHITE);
        
        // Home components - attr
        home_lbl.setBounds(10, 20, 300, 40);
        
        // Home Labels - attr
        welcome_lbl.setBounds(100, 60, 600, 500);
        
        // Home TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        
        // Adding all Home Panel variables
        Home_jpa.add(home_lbl);
        Home_jpa.add(welcome_lbl);
        Home_jpa.add(Login_jpa);
        
        // Home Layout - attr
        Home_jpa.setLayout(null);
        
    // -------------------------------------------------------------------------
        // Initializing the Dashboard Panel
        Dashboard_jpa = new JPanel();
        Dashboard_jpa.setBackground(new java.awt.Color(0, 51, 102));
        Dashboard_jpa.setBounds(0, 0, 1100, 700);
        
        // Dashboard - Labels
        dash_welcome_lbl = new JLabel();
        dash_welcome_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        dash_welcome_lbl.setForeground(Color.WHITE);
        add_quiz_lbl = new JLabel("Add Quiz");
        add_quiz_lbl.setFont(new Font("Arial", Font.PLAIN, 30));
        add_quiz_lbl.setForeground(Color.WHITE);
        all_quiz_lbl = new JLabel("All Quiz");
        all_quiz_lbl.setFont(new Font("Arial", Font.PLAIN, 30));
        all_quiz_lbl.setForeground(Color.WHITE);
        
        // Dashboard - Buttons
        logout_jbtn = new JButton("Logout");
        logout_jbtn.setCursor(cur);
        logout_jbtn.setBackground(new java.awt.Color(0, 51, 102));
        logout_jbtn.setForeground(Color.WHITE);
        logout_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        logout_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        logout_jbtn.setEnabled(connection);
        
        add_quiz_jbtn = new JButton("Add");
        add_quiz_jbtn.setCursor(cur);
        add_quiz_jbtn.setBackground(Color.blue);
        add_quiz_jbtn.setForeground(Color.WHITE);
        add_quiz_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        add_quiz_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                addQuizButtonActionPerformed(evt);
            }
        });
        add_quiz_jbtn.setEnabled(connection);
        
        all_quiz_jbtn = new JButton("View");
        all_quiz_jbtn.setCursor(cur);
        all_quiz_jbtn.setBackground(Color.green);
        all_quiz_jbtn.setForeground(Color.BLACK);
        all_quiz_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        all_quiz_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                allQuizButtonActionPerformed(evt);
            }
        });
        all_quiz_jbtn.setEnabled(connection);
        
        // Dashboard - Sub - Panels
    // -----------------------------------------
    
        Dash_edit_profile_jpa = new JPanel();
        Dash_edit_profile_jpa.setBackground(new java.awt.Color(0, 51, 102));
        
        // Dashboard - Sub - Labels
        dash_fname_lbl = new JLabel("First Name");
        dash_fname_lbl.setForeground(Color.WHITE);
        dash_fname_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        dash_lname_lbl = new JLabel("Last Name");
        dash_lname_lbl.setForeground(Color.WHITE);
        dash_lname_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        dash_email_lbl = new JLabel("Email");
        dash_email_lbl.setForeground(Color.WHITE);
        dash_email_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        dash_old_password_lbl = new JLabel("Old Password");
        dash_old_password_lbl.setForeground(Color.WHITE);
        dash_old_password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        dash_password_lbl = new JLabel("New Password");
        dash_password_lbl.setForeground(Color.WHITE);
        dash_password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        dash_confirm_password_lbl = new JLabel("Confirm Password");
        dash_confirm_password_lbl.setForeground(Color.WHITE);
        dash_confirm_password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Dashboard - Sub - Buttons
        dash_edit_profile_jbtn = new JButton("Edit Profile");
        dash_edit_profile_jbtn.setCursor(cur);
        dash_edit_profile_jbtn.setBackground(Color.blue);
        dash_edit_profile_jbtn.setForeground(Color.WHITE);
        dash_edit_profile_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        dash_edit_profile_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });
        dash_edit_profile_jbtn.setEnabled(connection);
        
        dash_save_changes_jbtn = new JButton("Save Changes");
        dash_save_changes_jbtn.setCursor(cur);
        dash_save_changes_jbtn.setBackground(Color.green);
        dash_save_changes_jbtn.setForeground(Color.BLACK);
        dash_save_changes_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        dash_save_changes_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveChangesButtonActionPerformed(evt);
            }
        });
        dash_save_changes_jbtn.setEnabled(connection);
        
        // Dashboard - Sub - TextFields
        dash_fname_txt = new JTextField(15);
        dash_fname_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        dash_fname_txt.setEnabled(false);
        dash_lname_txt = new JTextField(15);
        dash_lname_txt.setFont(new Font("Arial", Font.PLAIN, 18));
        dash_lname_txt.setEnabled(false);
        dash_email_txt = new JTextField(15);
        dash_email_txt.setFont(new Font("Arial", Font.PLAIN, 13));
        dash_email_txt.setEnabled(false);
        
        // Dashboard - Sub - PasswordFields
        dash_old_password_jpf = new JPasswordField(15);
        dash_old_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        dash_old_password_jpf.setEnabled(false);
        dash_password_jpf = new JPasswordField(15);
        dash_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        dash_password_jpf.setEnabled(false);
        dash_confirm_password_jpf = new JPasswordField(15);
        dash_confirm_password_jpf.setFont(new Font("Arial", Font.PLAIN, 18));
        dash_confirm_password_jpf.setEnabled(false);
        
        // Dashboard - Sub - Layout
        Dash_edit_profile_jpa.setLayout(null);
        
        // Dashboard - Sub - Panels - attr
        Dash_edit_profile_jpa.setBounds(630, 160, 400, 300);
        
        // Dashboard - Sub - Default Label
        edit_profile_lbl = new JLabel("Admin - Edit Profile");
        
        // Dashboard - Sub - Default Label - attr
        edit_profile_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        edit_profile_lbl.setForeground(Color.WHITE);
        
        // Dashboard - Sub - components - attr
        edit_profile_lbl.setBounds(10, 0, 300, 40);
        
        // Dashboard - Sub - Labels - attr
        dash_fname_lbl.setBounds(10, 50, 100, 30);
        dash_lname_lbl.setBounds(220, 50, 100, 30);
        dash_email_lbl.setBounds(10, 110, 100, 30);
        dash_old_password_lbl.setBounds(220, 110, 200, 30);
        dash_password_lbl.setBounds(10, 170, 200, 30);
        dash_confirm_password_lbl.setBounds(220, 170, 200, 30);
        
        // Dashboard - Sub - TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        dash_fname_txt.setBounds(10, 80, 160, 30);
        dash_lname_txt.setBounds(220, 80, 170, 30);
        dash_email_txt.setBounds(10, 140, 160, 30);
        dash_old_password_jpf.setBounds(220, 140, 170, 30);
        dash_password_jpf.setBounds(10, 210, 160, 30);
        dash_confirm_password_jpf.setBounds(220, 210, 170, 30);
        dash_edit_profile_jbtn.setBounds(100, 250, 200, 35);
        dash_save_changes_jbtn.setBounds(100, 250, 200, 35);
        
        // Adding all Dashboard - Sub - Panel variables
        Dash_edit_profile_jpa.add(edit_profile_lbl);
        Dash_edit_profile_jpa.add(dash_fname_lbl);
        Dash_edit_profile_jpa.add(dash_fname_txt);
        Dash_edit_profile_jpa.add(dash_lname_lbl);
        Dash_edit_profile_jpa.add(dash_lname_txt);
        Dash_edit_profile_jpa.add(dash_email_lbl);
        Dash_edit_profile_jpa.add(dash_email_txt);
        Dash_edit_profile_jpa.add(dash_old_password_lbl);
        Dash_edit_profile_jpa.add(dash_old_password_jpf);
        Dash_edit_profile_jpa.add(dash_password_lbl);
        Dash_edit_profile_jpa.add(dash_password_jpf);
        Dash_edit_profile_jpa.add(dash_confirm_password_lbl);
        Dash_edit_profile_jpa.add(dash_confirm_password_jpf);
        Dash_edit_profile_jpa.add(dash_edit_profile_jbtn);
        
        // Dashboard - Sub - Layout - attr
        Dash_edit_profile_jpa.setLayout(null);
    
    // -----------------------------------------
        
        // Dashboard Layout
        Dashboard_jpa.setLayout(null);
        
        // Dashboard Panels - attr
        
        // Dashboard Default Label
        dashboard_lbl = new JLabel("Admin - Dashboard");
        
        // Dashboard Default Label - attr
        dashboard_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        dashboard_lbl.setForeground(Color.WHITE);
        
        // Dashboard components - attr
        dashboard_lbl.setBounds(10, 20, 300, 40);
        
        // Dashboard Labels - attr
        dash_welcome_lbl.setBounds(10, 90, 400, 40);
        add_quiz_lbl.setBounds(50, 240, 200, 40);
        all_quiz_lbl.setBounds(50, 320, 200, 40);
        
        // Dashboard TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        logout_jbtn.setBounds(950, 30, 100, 30);
        add_quiz_jbtn.setBounds(260, 240, 100, 40);
        all_quiz_jbtn.setBounds(260, 320, 100, 40);
        
        // Adding all Dashboard Panel variables
        Dashboard_jpa.add(dashboard_lbl);
        Dashboard_jpa.add(logout_jbtn);
        Dashboard_jpa.add(dash_welcome_lbl);
        Dashboard_jpa.add(add_quiz_lbl);
        Dashboard_jpa.add(add_quiz_jbtn);
        Dashboard_jpa.add(all_quiz_lbl);
        Dashboard_jpa.add(all_quiz_jbtn);
        Dashboard_jpa.add(Dash_edit_profile_jpa);
        
        // Dashboard Layout - attr
        Dashboard_jpa.setLayout(null);
        
    // -------------------------------------------------------------------------
        // Initializing the Question Panel
        Question_jpa = new JPanel();
        Question_jpa.setBackground(new java.awt.Color(0, 51, 102));
        Question_jpa.setBounds(0, 0, 1100, 700);
        
        // Question - Labels
        add_question_lbl = new JLabel("Add New Question");
        add_question_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        add_question_lbl.setForeground(Color.WHITE);
        new_question_lbl = new JLabel("Question");
        new_question_lbl.setForeground(Color.WHITE);
        new_question_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        option_1_lbl = new JLabel("Option 1");
        option_1_lbl.setForeground(Color.WHITE);
        option_1_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        option_2_lbl = new JLabel("Option 2");
        option_2_lbl.setForeground(Color.WHITE);
        option_2_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        option_3_lbl = new JLabel("Option 3");
        option_3_lbl.setForeground(Color.WHITE);
        option_3_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        option_4_lbl = new JLabel("Option 4");
        option_4_lbl.setForeground(Color.WHITE);
        option_4_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        answer_lbl = new JLabel("Answer");
        answer_lbl.setForeground(Color.WHITE);
        answer_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
                
        // Question - Buttons
        save_question_jbtn = new JButton("Save Question");
        save_question_jbtn.setCursor(cur);
        save_question_jbtn.setBackground(Color.green);
        save_question_jbtn.setForeground(Color.BLACK);
        save_question_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        save_question_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveQuestionButtonActionPerformed(evt);
            }
        });
        save_question_jbtn.setEnabled(connection);
        
        question_back_to_dashboard_jbtn = new JButton("Back");
        question_back_to_dashboard_jbtn.setCursor(cur);
        question_back_to_dashboard_jbtn.setBackground(new java.awt.Color(0, 51, 102));
        question_back_to_dashboard_jbtn.setForeground(Color.WHITE);
        question_back_to_dashboard_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        question_back_to_dashboard_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                question_back_to_dashboardButtonActionPerformed(evt);
            }
        });
        question_back_to_dashboard_jbtn.setEnabled(connection);
        
        // Question ScrollPane
        new_question_scroll = new JScrollPane();
        option_1_scroll = new JScrollPane();
        option_2_scroll = new JScrollPane();
        option_3_scroll = new JScrollPane();
        option_4_scroll = new JScrollPane();
        
        // Question TextAreas
        new_question_jta = new JTextArea(5, 20);
        new_question_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        new_question_jta.setLineWrap(true);
        new_question_scroll.setViewportView(new_question_jta);
        option_1_jta = new JTextArea(5, 20);
        option_1_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        option_1_jta.setLineWrap(true);
        option_1_scroll.setViewportView(option_1_jta);
        option_2_jta = new JTextArea(5, 20);
        option_2_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        option_2_jta.setLineWrap(true);
        option_2_scroll.setViewportView(option_2_jta);
        option_3_jta = new JTextArea(5, 20);
        option_3_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        option_3_jta.setLineWrap(true);
        option_3_scroll.setViewportView(option_3_jta);
        option_4_jta = new JTextArea(5, 20);
        option_4_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        option_4_jta.setLineWrap(true);
        option_4_scroll.setViewportView(option_4_jta);
        
        // Question ComboBoxes
        answer_jcb = new JComboBox<>();
        answer_jcb.addItem("Option 1");
        answer_jcb.addItem("Option 2");
        answer_jcb.addItem("Option 3");
        answer_jcb.addItem("Option 4");
        
        // Question Layout
        Question_jpa.setLayout(null);
        
        // Question Panels - attr
        
        // Question Default Label
        question_lbl = new JLabel("Admin - Quiz Questions");
        
        // Question Default Label - attr
        question_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        question_lbl.setForeground(Color.WHITE);
        
        // Question components - attr
        question_lbl.setBounds(10, 20, 400, 40);
        
        // Question Labels - attr
        add_question_lbl.setBounds(10, 90, 400, 40);
        new_question_lbl.setBounds(100, 150, 100, 30);
        option_1_lbl.setBounds(100, 280, 400, 30);
        option_2_lbl.setBounds(100, 410, 400, 30);
        option_3_lbl.setBounds(550, 150, 400, 30);
        option_4_lbl.setBounds(550, 280, 400, 30);
        answer_lbl.setBounds(550, 410, 400, 30);
        
        // Question TextFields / TextAreas / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        new_question_scroll.setBounds(100, 190, 400, 80);
        option_1_scroll.setBounds(100, 320, 400, 80);
        option_2_scroll.setBounds(100, 450, 400, 80);
        option_3_scroll.setBounds(550, 190, 400, 80);
        option_4_scroll.setBounds(550, 320, 400, 80);
        answer_jcb.setBounds(550, 450, 400, 30);
        save_question_jbtn.setBounds(550, 500, 400, 30);
        question_back_to_dashboard_jbtn.setBounds(950, 30, 100, 30);
        
        // Adding all Question Panel variables
        Question_jpa.add(question_lbl);
        Question_jpa.add(add_question_lbl);
        Question_jpa.add(new_question_lbl);
        Question_jpa.add(new_question_scroll);
        Question_jpa.add(option_1_lbl);
        Question_jpa.add(option_1_scroll);
        Question_jpa.add(option_2_lbl);
        Question_jpa.add(option_2_scroll);
        Question_jpa.add(option_3_lbl);
        Question_jpa.add(option_3_scroll);
        Question_jpa.add(option_4_lbl);
        Question_jpa.add(option_4_scroll);
        Question_jpa.add(answer_lbl);
        Question_jpa.add(answer_jcb);
        Question_jpa.add(save_question_jbtn);
        Question_jpa.add(question_back_to_dashboard_jbtn);
        
        // Question Layout - attr
        Question_jpa.setLayout(null);
        
    // -------------------------------------------------------------------------
        // Initializing the Edit Question Panel
        Edit_Question_jpa = new JPanel();
        Edit_Question_jpa.setBackground(new java.awt.Color(0, 51, 102));
        Edit_Question_jpa.setBounds(0, 0, 1100, 700);
        
        // Edit Question - Labels
        edit_add_question_lbl = new JLabel("Edit Question");
        edit_add_question_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        edit_add_question_lbl.setForeground(Color.WHITE);
        edit_new_question_lbl = new JLabel("Question");
        edit_new_question_lbl.setForeground(Color.WHITE);
        edit_new_question_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        edit_option_1_lbl = new JLabel("Option 1");
        edit_option_1_lbl.setForeground(Color.WHITE);
        edit_option_1_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        edit_option_2_lbl = new JLabel("Option 2");
        edit_option_2_lbl.setForeground(Color.WHITE);
        edit_option_2_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        edit_option_3_lbl = new JLabel("Option 3");
        edit_option_3_lbl.setForeground(Color.WHITE);
        edit_option_3_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        edit_option_4_lbl = new JLabel("Option 4");
        edit_option_4_lbl.setForeground(Color.WHITE);
        edit_option_4_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        edit_answer_lbl = new JLabel("Answer");
        edit_answer_lbl.setForeground(Color.WHITE);
        edit_answer_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
                
        // Edit Question - Buttons
        edit_save_question_jbtn = new JButton("Save Changes");
        edit_save_question_jbtn.setCursor(cur);
        edit_save_question_jbtn.setBackground(Color.green);
        edit_save_question_jbtn.setForeground(Color.BLACK);
        edit_save_question_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        edit_save_question_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveQuestionChangesButtonActionPerformed(evt);
            }
        });
        edit_save_question_jbtn.setEnabled(connection);
        
        edit_question_back_to_dashboard_jbtn = new JButton("Back");
        edit_question_back_to_dashboard_jbtn.setCursor(cur);
        edit_question_back_to_dashboard_jbtn.setBackground(new java.awt.Color(0, 51, 102));
        edit_question_back_to_dashboard_jbtn.setForeground(Color.WHITE);
        edit_question_back_to_dashboard_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        edit_question_back_to_dashboard_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                edit_question_back_to_dashboardButtonActionPerformed(evt);
            }
        });
        edit_question_back_to_dashboard_jbtn.setEnabled(connection);
        
        // Edit Question ScrollPane
        edit_new_question_scroll = new JScrollPane();
        edit_option_1_scroll = new JScrollPane();
        edit_option_2_scroll = new JScrollPane();
        edit_option_3_scroll = new JScrollPane();
        edit_option_4_scroll = new JScrollPane();
        
        // Edit Question TextAreas
        edit_new_question_jta = new JTextArea(5, 20);
        edit_new_question_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        edit_new_question_jta.setLineWrap(true);
        edit_new_question_scroll.setViewportView(edit_new_question_jta);
        edit_option_1_jta = new JTextArea(5, 20);
        edit_option_1_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        edit_option_1_jta.setLineWrap(true);
        edit_option_1_scroll.setViewportView(edit_option_1_jta);
        edit_option_2_jta = new JTextArea(5, 20);
        edit_option_2_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        edit_option_2_jta.setLineWrap(true);
        edit_option_2_scroll.setViewportView(edit_option_2_jta);
        edit_option_3_jta = new JTextArea(5, 20);
        edit_option_3_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        edit_option_3_jta.setLineWrap(true);
        edit_option_3_scroll.setViewportView(edit_option_3_jta);
        edit_option_4_jta = new JTextArea(5, 20);
        edit_option_4_jta.setFont(new Font("Arial", Font.PLAIN, 15));
        edit_option_4_jta.setLineWrap(true);
        edit_option_4_scroll.setViewportView(edit_option_4_jta);
        
        // Edit Question ComboBoxes
        edit_answer_jcb = new JComboBox<>();
        edit_answer_jcb.addItem("Option 1");
        edit_answer_jcb.addItem("Option 2");
        edit_answer_jcb.addItem("Option 3");
        edit_answer_jcb.addItem("Option 4");
        
        // Edit Question Layout
        Edit_Question_jpa.setLayout(null);
        
        // Edit Question Panels - attr
        
        // Edit Question Default Label
        edit_question_lbl = new JLabel("Admin - Quiz Questions");
        
        // Edit Question Default Label - attr
        edit_question_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        edit_question_lbl.setForeground(Color.WHITE);
        
        // Edit Question components - attr
        edit_question_lbl.setBounds(10, 20, 400, 40);
        
        // Edit Question Labels - attr
        edit_add_question_lbl.setBounds(10, 90, 400, 40);
        edit_new_question_lbl.setBounds(100, 150, 100, 30);
        edit_option_1_lbl.setBounds(100, 280, 400, 30);
        edit_option_2_lbl.setBounds(100, 410, 400, 30);
        edit_option_3_lbl.setBounds(550, 150, 400, 30);
        edit_option_4_lbl.setBounds(550, 280, 400, 30);
        edit_answer_lbl.setBounds(550, 410, 400, 30);
        
        // Edit Question TextFields / TextAreas / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        edit_new_question_scroll.setBounds(100, 190, 400, 80);
        edit_option_1_scroll.setBounds(100, 320, 400, 80);
        edit_option_2_scroll.setBounds(100, 450, 400, 80);
        edit_option_3_scroll.setBounds(550, 190, 400, 80);
        edit_option_4_scroll.setBounds(550, 320, 400, 80);
        edit_answer_jcb.setBounds(550, 450, 400, 30);
        edit_save_question_jbtn.setBounds(550, 500, 400, 30);
        edit_question_back_to_dashboard_jbtn.setBounds(950, 30, 100, 30);
        
        // Adding all Edit Question Panel variables
        Edit_Question_jpa.add(edit_question_lbl);
        Edit_Question_jpa.add(edit_add_question_lbl);
        Edit_Question_jpa.add(edit_new_question_lbl);
        Edit_Question_jpa.add(edit_new_question_scroll);
        Edit_Question_jpa.add(edit_option_1_lbl);
        Edit_Question_jpa.add(edit_option_1_scroll);
        Edit_Question_jpa.add(edit_option_2_lbl);
        Edit_Question_jpa.add(edit_option_2_scroll);
        Edit_Question_jpa.add(edit_option_3_lbl);
        Edit_Question_jpa.add(edit_option_3_scroll);
        Edit_Question_jpa.add(edit_option_4_lbl);
        Edit_Question_jpa.add(edit_option_4_scroll);
        Edit_Question_jpa.add(edit_answer_lbl);
        Edit_Question_jpa.add(edit_answer_jcb);
        Edit_Question_jpa.add(edit_save_question_jbtn);
        Edit_Question_jpa.add(edit_question_back_to_dashboard_jbtn);
        
        // Edit Question Layout - attr
        Edit_Question_jpa.setLayout(null);
        
    // -------------------------------------------------------------------------
        // Initializing the AllQuiz Panel
        AllQuiz_jpa = new JPanel();
        AllQuiz_jpa.setBackground(new java.awt.Color(0, 51, 102));
        AllQuiz_jpa.setBounds(0, 0, 1100, 700);
        
        // AllQuiz - Labels
        
        // AllQuiz - Buttons
        allQuiz_back_to_dashboard_jbtn = new JButton("Back");
        allQuiz_back_to_dashboard_jbtn.setCursor(cur);
        allQuiz_back_to_dashboard_jbtn.setBackground(new java.awt.Color(0, 51, 102));
        allQuiz_back_to_dashboard_jbtn.setForeground(Color.WHITE);
        allQuiz_back_to_dashboard_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        allQuiz_back_to_dashboard_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                allQuiz_back_to_dashboardButtonActionPerformed(evt);
            }
        });
        allQuiz_back_to_dashboard_jbtn.setEnabled(connection);
        
        allQuiz_refresh_jbtn = new JButton("Refresh");
        allQuiz_refresh_jbtn.setCursor(cur);
        allQuiz_refresh_jbtn.setBackground(new java.awt.Color(0, 51, 102));
        allQuiz_refresh_jbtn.setForeground(Color.WHITE);
        allQuiz_refresh_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        allQuiz_refresh_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                allQuizRefreshButtonActionPerformed(evt);
            }
        });
        allQuiz_refresh_jbtn.setEnabled(connection);
        
        // AllQuiz Lists / DefaultlistModels / Scrolls / Tables
        allQuiz_tbl = new JTable();
        allQuiz_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        dtm_allQuiz = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        allQuiz_tbl.setModel(dtm_allQuiz);
        allQuiz_tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseRightClick(e);
            }
            public void mouseReleased(MouseEvent e){
                mouseRightClick(e);
            }

        });
        allQuiz_scroll = new JScrollPane();
        allQuiz_scroll.setViewportView(allQuiz_tbl);
        
        //  Creating the PopUpMenu
        right_click_jmu = new JPopupMenu("Options");
        
        // AllQuiz Adding Items to the PopUpMenu
        right_click_edit_jmi = new JMenuItem("Edit");
        right_click_edit_jmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        right_click_jmu.add(right_click_edit_jmi);
        
        right_click_delete_jmi = new JMenuItem("Delete");
        right_click_delete_jmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        right_click_jmu.add(right_click_delete_jmi);
        
        // AllQuiz Layout
        AllQuiz_jpa.setLayout(null);
        
        // AllQuiz Panels - attr
        
        // AllQuiz Default Label
        allQuiz_lbl = new JLabel("Admin - All Quiz");
        
        // AllQuiz Default Label - attr
        allQuiz_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        allQuiz_lbl.setForeground(Color.WHITE);
        
        // AllQuiz components - attr
        allQuiz_lbl.setBounds(10, 20, 400, 40);
        
        // AllQuiz Labels - attr
        
        // AllQuiz Table - attr
        dtm_allQuiz.addColumn("ID");
        dtm_allQuiz.addColumn("Admin ID");
        dtm_allQuiz.addColumn("Question");
        dtm_allQuiz.addColumn("Option 1");
        dtm_allQuiz.addColumn("Option 2");
        dtm_allQuiz.addColumn("Option 3");
        dtm_allQuiz.addColumn("Option 4");
        dtm_allQuiz.addColumn("Answer");
        dtm_allQuiz.addColumn("Date");
        
        allQuiz_tbl.getTableHeader().setReorderingAllowed(false);
        allQuiz_tbl.getTableHeader().setResizingAllowed(false);
        
        // AllQuiz TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        allQuiz_refresh_jbtn.setBounds(830, 30, 100, 30);
        allQuiz_back_to_dashboard_jbtn.setBounds(950, 30, 100, 30);
        allQuiz_scroll.setBounds(10, 80, 1073, 550); // after adding the member panel you put the panel in the scrollpane then set bounds for the scrollpane
        
        // Adding all AllQuiz Panel variables
        AllQuiz_jpa.add(allQuiz_lbl);
        AllQuiz_jpa.add(allQuiz_refresh_jbtn);
        AllQuiz_jpa.add(allQuiz_scroll);
        AllQuiz_jpa.add(allQuiz_back_to_dashboard_jbtn);
        
        // AllQuiz Layout - attr
        AllQuiz_jpa.setLayout(null);
        
    // -------------------------------------------------------------------------
    
        // Adding Home Panel to Layer Panel
        Layer_jpa.add(Home_jpa);
    
        MainFrame.add(Layer_jpa);
        
        MainFrame.setSize(1100, 700);
        MainFrame.setTitle("Quiz App - Admin");
        MainFrame.setVisible(true);
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setResizable(false);
        MainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/quizapp/images/Icon 2.PNG")));
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Setting up SQL Connection
    private void setConnection(){
        // Setup SQL connection
        try 
        {

            Class.forName("com.mysql.jdbc.Driver");

            sql_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
            sql_stmt = sql_con.createStatement();
            
            // clear everything here
            
            connection = true;
            
            // enable everything here (If connection successful)
            logout_jbtn.setEnabled(connection);
            add_quiz_jbtn.setEnabled(connection);
            all_quiz_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            save_question_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            edit_save_question_jbtn.setEnabled(connection);
            edit_question_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_refresh_jbtn.setEnabled(connection);
            
            reload_questions();
            
            JOptionPane.showMessageDialog(MainFrame, "Connected", "Connection Successful", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (IllegalStateException ex){
            // If connection not successful disable all buttons
            connection = false;
            logout_jbtn.setEnabled(connection);
            add_quiz_jbtn.setEnabled(connection);
            all_quiz_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            save_question_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            edit_save_question_jbtn.setEnabled(connection);
            edit_question_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_refresh_jbtn.setEnabled(connection);
            
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Connection Error", JOptionPane.ERROR_MESSAGE);
            
        }
        catch (Exception e) 
        {
            // If connection not successful disable all buttons
            connection = false;
            logout_jbtn.setEnabled(connection);
            add_quiz_jbtn.setEnabled(connection);
            all_quiz_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            save_question_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            edit_save_question_jbtn.setEnabled(connection);
            edit_question_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_back_to_dashboard_jbtn.setEnabled(connection);
            allQuiz_refresh_jbtn.setEnabled(connection);
            
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Connection Error", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    private void loginAdmin(){
        if (connection) {
            synchronized(this){
                String email = log_email_txt.getText();
                String password = log_password_jpf.getText();
                String email_check = check_email(email);
                String email_not_exist = log_check_email_exist(email);
                
                if(log_email_txt.getText().equals("") || log_password_jpf.getText().equals("")){
                    JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if (email_check.equals("email_error")) {
                        JOptionPane.showMessageDialog(MainFrame, "Incorrect email", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                    else if(email_not_exist.equals("not_exist")){
                        JOptionPane.showMessageDialog(MainFrame, "Email does not exist", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

                        try 
                        {
                            String str = "SELECT * FROM admin WHERE email = ?";

                            Class.forName("com.mysql.jdbc.Driver");

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                            {
                                sql_stat = con.prepareStatement(str);

                                sql_stat.setString(1, email);

                                ResultSet rs = sql_stat.executeQuery();

                                while (rs.next()) 
                                {
                                    String db_pword = rs.getString("password");
                                    if(password.equalsIgnoreCase(db_pword)){
                                        JOptionPane.showMessageDialog(MainFrame, "Logged in successfully", "Success message", JOptionPane.PLAIN_MESSAGE);
                                        Layer_jpa.updateUI();
                                        Layer_jpa.remove(Home_jpa);
                                        Layer_jpa.updateUI();
                                        Layer_jpa.add(Dashboard_jpa);
                                        Layer_jpa.updateUI();

                                        logged_in_id = rs.getString("id");
                                        logged_in_fname = rs.getString("firstName");
                                        logged_in_lname = rs.getString("lastName");
                                        logged_in_email = rs.getString("email");
                                        logged_in_password = rs.getString("password");
                                        
                                        dash_welcome_lbl.setText(logged_in_email);
                                        dash_fname_txt.setText(logged_in_fname);
                                        dash_lname_txt.setText(logged_in_lname);
                                        dash_email_txt.setText(logged_in_email);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(MainFrame, "Incorrect password", "Warning Message", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        catch (Exception e){
                            JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Error Message" , JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Check if email already exists before add new Admins
    private String reg_check_email_exist(String email){
        String Email = email;
        
        synchronized(this) {
            try 
            {
                String str = "SELECT * FROM admin";

                Class.forName("com.mysql.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                        Statement stmt = con.createStatement();) 
                {
                    ResultSet rs = stmt.executeQuery(str);

                    while (rs.next()) 
                    {
                        String db_email = rs.getString("email");
                        if(Email.equalsIgnoreCase(db_email)){
                            Email = "email_exists";
                        }
                    }
                }
            } 
            catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
        return Email;
    }
    
    // Check if there is any registered Admins yet
    private void check_for_admin(){
        synchronized(this) {
            try 
            {
                String str = "SELECT * FROM admin";

                Class.forName("com.mysql.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                        Statement stmt = con.createStatement();) 
                {
                    ResultSet rs = stmt.executeQuery(str);

                    if(!(rs.next())){
                        admin = "no";
                    }
                    else {
                        admin = "yes";
                    }
                    
                }
            } 
            catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Checking if email exist before logging in
    private String log_check_email_exist(String email){
        check_for_admin();
        String Email = "";
        
        if(admin.equals("no")){
            JOptionPane.showMessageDialog(MainFrame, "No admin yet, Sign up ...", "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
        else {
            synchronized(this) {
                try 
                {
                    String str = "SELECT * FROM admin";

                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                            Statement stmt = con.createStatement();) 
                    {
                        ResultSet rs = stmt.executeQuery(str);

                        while (rs.next()) 
                        {
                            String db_email = rs.getString("email");
                            if(email.equals(db_email)){
                                Email = email;
                                break;
                            }
                            else {
                                Email = "not_exist"; 
                            }
                        }

                    }
                } 
                catch (SQLException ex) 
                {
                    JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        return Email;
    }
    
    // Check for correct email
    private String check_email(String email){
        String Email = email;
        
        Pattern email_pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        
        Matcher email_macther = email_pattern.matcher(Email);
        
        boolean email_boolean = email_macther.matches();
        
        if(email_boolean){
            return Email;
        }
        else {
            return "email_error";
        }
    }
    
    // Action Performed on Refresh Menu button 
    private void refreshButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            JOptionPane.showMessageDialog(MainFrame, "Connecting ...", "Information Message", JOptionPane.INFORMATION_MESSAGE);
            // Resetting SQL connection
        
            setConnection();
        }
    }
    
    // Logging in Admins
    private void loginButtonActionPerformed(ActionEvent evt){
        loginAdmin();
    }
    
    // Switching to the Register Panel
    private void go_to_registerButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            Home_jpa.updateUI();
            Home_jpa.remove(Login_jpa);
            Home_jpa.updateUI();
            Home_jpa.add(Register_jpa);
            Home_jpa.updateUI();
        }
    }
    
    // Registering new Admins
    private void registerButtonActionPerformed(ActionEvent evt){
        if(connection){
            synchronized(this){
                String email_exist = reg_check_email_exist(reg_email_txt.getText());
                String email_check = check_email(reg_email_txt.getText());
                try 
                {
                    if(fname_txt.getText().equals("") || lname_txt.getText().equals("") || reg_email_txt.getText().equals("") 
                       || reg_password_jpf.getText().equals("") || confirm_password_jpf.getText().equals("")){
                        JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                    else{

                        if(email_exist.equals("email_exists")){
                            JOptionPane.showMessageDialog(MainFrame, "Email already exists, Going to Login ...", "Error Message", JOptionPane.ERROR_MESSAGE);
                            Home_jpa.updateUI();
                            Home_jpa.remove(Register_jpa);
                            Home_jpa.updateUI();
                            Home_jpa.add(Login_jpa);
                            Home_jpa.updateUI();
                            
                            log_email_txt.setText(reg_email_txt.getText());
                        }
                        else{

                            if (email_check.equals("email_error")) {
                                JOptionPane.showMessageDialog(MainFrame, "Incorrect email", "Warning Message", JOptionPane.WARNING_MESSAGE);
                            }
                            else{

                                if(reg_password_jpf.getText().equals(confirm_password_jpf.getText())){
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                    Date dateN = new Date();
                                    String Date = dateFormat.format(dateN);

                                    String first_name = fname_txt.getText();
                                    String last_name = lname_txt.getText();
                                    String email = email_exist;
                                    String password = reg_password_jpf.getText();

                                    // Inserting new registered members (Admins) in DB (using prepared statement)
                                    sql_stat = sql_con.prepareStatement("INSERT INTO admin (firstName, lastName, email, password, added_date) VALUES(?, ?, ?, ?, ?)");

                                    sql_stat.setString(1, first_name);
                                    sql_stat.setString(2, last_name);
                                    sql_stat.setString(3, email);
                                    sql_stat.setString(4, password);
                                    sql_stat.setString(5, Date);

                                    sql_stat.execute();

                                    JOptionPane.showMessageDialog(MainFrame, "Registered successfully", "Success message", JOptionPane.PLAIN_MESSAGE);

                                    log_email_txt.setText(reg_email_txt.getText());
                                    
                                    fname_txt.setText(null);
                                    lname_txt.setText(null);
                                    reg_email_txt.setText(null);
                                    reg_password_jpf.setText(null);
                                    confirm_password_jpf.setText(null);

                                    Home_jpa.updateUI();
                                    Home_jpa.remove(Register_jpa);
                                    Home_jpa.updateUI();
                                    Home_jpa.add(Login_jpa);
                                    Home_jpa.updateUI();
                                }
                                else {
                                    JOptionPane.showMessageDialog(MainFrame, "Confirm password doesn't match, try again", "Warning message", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    }
                } 
                catch (SQLException ex) 
                {

                    String msg = ex.getMessage();

                    if(ex.getMessage().equals(msg))
                    {
                        JOptionPane.showMessageDialog(MainFrame, "SQL Error : " + msg + "\n Code : " + ex.getErrorCode(), "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }

                }
                catch (Exception e){
                    String msg = e.getLocalizedMessage();
                    JOptionPane.showMessageDialog(MainFrame, "Error : " + msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Error Message" , JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Switching to the Login Panel
    private void go_to_loginButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            Home_jpa.updateUI();
            Home_jpa.remove(Register_jpa);
            Home_jpa.updateUI();
            Home_jpa.add(Login_jpa);
            Home_jpa.updateUI();
        }
    }
    
    // Switching to the Add Question Panel
    private void addQuizButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            Layer_jpa.updateUI();
            Layer_jpa.remove(Dashboard_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Question_jpa);
            Layer_jpa.updateUI();
        }
    }
    
    // Switching to the All Question Panel
    private void allQuizButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            Layer_jpa.updateUI();
            Layer_jpa.remove(Dashboard_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(AllQuiz_jpa);
            Layer_jpa.updateUI();
        }
    }
    
    // Saving each question added to DB
    private void saveQuestionButtonActionPerformed(ActionEvent evt){
        if (connection) {
            synchronized(this){
                try 
                {
                    if (new_question_jta.getText().equals("") || option_1_jta.getText().equals("") || option_2_jta.getText().equals("") ||
                        option_3_jta.getText().equals("") || option_4_jta.getText().equals("")) {
                        JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning message", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        if (new_question_jta.getText().length() > 90 || option_1_jta.getText().length() > 90 || option_2_jta.getText().length() > 90 ||
                        option_3_jta.getText().length() > 90 || option_4_jta.getText().length() > 90) {
                            JOptionPane.showMessageDialog(MainFrame, "Question / Options must not exceed 90 characters", "Warning message", JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                            Date dateN = new Date();
                            String Date = dateFormat.format(dateN);

                            String question = new_question_jta.getText();
                            String option1 = option_1_jta.getText();
                            String option2 = option_2_jta.getText();
                            String option3 = option_3_jta.getText();
                            String option4 = option_4_jta.getText();
                            String answer = answer_jcb.getSelectedItem().toString();

                            // Inserting new registered members (Admins) in DB (using prepared statement)
                            sql_stat = sql_con.prepareStatement("INSERT INTO questions (admin_id, question, option1, option2, option3, option4, answer, added_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                            sql_stat.setString(1, logged_in_id);
                            sql_stat.setString(2, question);
                            sql_stat.setString(3, option1);
                            sql_stat.setString(4, option2);
                            sql_stat.setString(5, option3);
                            sql_stat.setString(6, option4);
                            sql_stat.setString(7, answer);
                            sql_stat.setString(8, Date);

                            sql_stat.execute();

                            JOptionPane.showMessageDialog(MainFrame, "Question saved successfully", "Success message", JOptionPane.PLAIN_MESSAGE);

                            new_question_jta.setText(null);
                            option_1_jta.setText(null);
                            option_2_jta.setText(null);
                            option_3_jta.setText(null);
                            option_4_jta.setText(null);
                            answer_jcb.setSelectedIndex(0);
                        }
                    }
                } 
                catch (SQLException ex) 
                {

                    String msg = ex.getMessage();

                    if(ex.getMessage().equals(msg))
                    {
                        JOptionPane.showMessageDialog(MainFrame, "SQL Error : " + msg + "\n Code : " + ex.getErrorCode(), "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }

                }
                catch (Exception e){
                    String msg = e.getLocalizedMessage();
                    JOptionPane.showMessageDialog(MainFrame, "Error : " + msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Saving each question edited and added to DB
    private void saveQuestionChangesButtonActionPerformed(ActionEvent evt){
        if (connection) {
            synchronized(this){
                try 
                {
                    if (edit_new_question_jta.getText().equals("") || edit_option_1_jta.getText().equals("") || edit_option_2_jta.getText().equals("") ||
                        edit_option_3_jta.getText().equals("") || edit_option_4_jta.getText().equals("")) {
                        JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning message", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        if (edit_new_question_jta.getText().length() > 90 || edit_option_1_jta.getText().length() > 90 || edit_option_2_jta.getText().length() > 90 ||
                        edit_option_3_jta.getText().length() > 90 || edit_option_4_jta.getText().length() > 90) {
                            JOptionPane.showMessageDialog(MainFrame, "Question / Options must not exceed 90 characters", "Warning message", JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            String question = edit_new_question_jta.getText();
                            String option1 = edit_option_1_jta.getText();
                            String option2 = edit_option_2_jta.getText();
                            String option3 = edit_option_3_jta.getText();
                            String option4 = edit_option_4_jta.getText();
                            String answer = edit_answer_jcb.getSelectedItem().toString();

                            Class.forName("com.mysql.jdbc.Driver");

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                            {

                                String response = "";
                                synchronized (this){
                                    response = updateQuestion(global_edit_question_id, logged_in_id, question, option1, option2, option3, option4, answer);
                                }
                                if(response.equals("Successful")){
                                    JOptionPane.showMessageDialog(MainFrame, "Changes Saved");
                                    reload_questions();

                                    edit_new_question_jta.setText(null);
                                    edit_option_1_jta.setText(null);
                                    edit_option_2_jta.setText(null);
                                    edit_option_3_jta.setText(null);
                                    edit_option_4_jta.setText(null);
                                    edit_answer_jcb.setSelectedIndex(0);

                                    synchronized(this){
                                        Layer_jpa.updateUI();
                                        Layer_jpa.remove(Edit_Question_jpa);
                                        Layer_jpa.updateUI();
                                        Layer_jpa.add(AllQuiz_jpa);
                                        Layer_jpa.updateUI();
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(MainFrame, "An Error Occured", "Error Message", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } 
                catch (SQLException ex) 
                {
                    String msg = ex.getMessage();

                    if(ex.getMessage().equals(msg))
                    {
                        JOptionPane.showMessageDialog(MainFrame, "SQL Error : " + msg + "\n Code : " + ex.getErrorCode(), "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception e){
                    String msg = e.getLocalizedMessage();
                    JOptionPane.showMessageDialog(MainFrame, "Error : " + msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Warning Message", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Editing Admin's profile details
    private void editProfileButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            dash_fname_txt.setEnabled(true);
            dash_lname_txt.setEnabled(true);
            dash_old_password_jpf.setEnabled(true);
            dash_password_jpf.setEnabled(true);
            dash_confirm_password_jpf.setEnabled(true);
            
            Dash_edit_profile_jpa.remove(dash_edit_profile_jbtn);
            Dash_edit_profile_jpa.updateUI();
            Dash_edit_profile_jpa.add(dash_save_changes_jbtn);
            Dash_edit_profile_jpa.updateUI();
        }
    }
    
    // Update a users (Admin) value In Database
    private String updateValue(String fname, String lname, String password, String email){
        String response = "";
        synchronized(this){
            try 
            {
                String str = "UPDATE admin SET firstName = ?, lastName = ?, password = ?, added_date = ? WHERE email = ? ";

                Class.forName("com.mysql.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                {
                    sql_stat = con.prepareStatement(str);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date dateN = new Date();
                    String Date = dateFormat.format(dateN);
                    
                    sql_stat.setString(1, fname);
                    sql_stat.setString(2, lname);
                    sql_stat.setString(3, password);
                    sql_stat.setString(4, Date);
                    sql_stat.setString(5, email);

                    sql_stat.execute();
                    
                    response = "Successful";
                    
                    sql_stat.clearParameters();
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex);
                response = "Error Occured";
            }
            catch (Exception e){
                System.out.println(e);
                response = "Error Occured";
            }
        }
        return response;
    }
    
    // Update a question's value In Database
    private String updateQuestion(String question_id, String admin_id, String question, String option1, String option2, String option3, String option4, String answer){
        String response = "";
        synchronized(this){
            try 
            {
                String str = "UPDATE questions SET admin_id = ?, question = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, answer = ?,  added_date = ? WHERE id = ? ";

                Class.forName("com.mysql.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                {
                    sql_stat = con.prepareStatement(str);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date dateN = new Date();
                    String Date = dateFormat.format(dateN);
                    
                    sql_stat.setString(1, admin_id);
                    sql_stat.setString(2, question);
                    sql_stat.setString(3, option1);
                    sql_stat.setString(4, option2);
                    sql_stat.setString(5, option3);
                    sql_stat.setString(6, option4);
                    sql_stat.setString(7, answer);
                    sql_stat.setString(8, Date);
                    sql_stat.setString(9, question_id);

                    sql_stat.execute();
                    
                    response = "Successful";
                    
                    sql_stat.clearParameters();
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex);
                response = "Error Occured";
            }
            catch (Exception e){
                System.out.println(e);
                response = "Error Occured";
            }
        }
        return response;
    }
    
    // Saving changes made to profile
    private void saveChangesButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            // For existing logged in First Name
            String fname = dash_fname_txt.getText();
            // For existing logged in Last Name
            String lname = dash_lname_txt.getText();
            // For new password
            String password = dash_password_jpf.getText();
            // For old password
            String empty_password = logged_in_password;
            // For existing logged in email
            String email = logged_in_email;
            
            // Check if the password wasn't chnaged
            if(dash_old_password_jpf.getText().equals("") || dash_password_jpf.getText().equals("") || dash_confirm_password_jpf.getText().equals("")){
                try 
                {
                    Class.forName("com.mysql.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                    {
                        
                        String response = "";
                        // Update values based on old password value
                        synchronized (this){
                            response = updateValue(fname, lname, empty_password, email);
                        }
                        if(response.equals("Successful")){
                            JOptionPane.showMessageDialog(MainFrame, "Changes Saved");
                            
                            logged_in_fname = fname;
                            logged_in_lname = lname;
                            logged_in_password = empty_password;
                            
                            dash_fname_txt.setText(logged_in_fname);
                            dash_lname_txt.setText(logged_in_lname);

                            dash_old_password_jpf.setText(null);
                            dash_password_jpf.setText(null);
                            dash_confirm_password_jpf.setText(null);
                            
                            dash_fname_txt.setEnabled(false);
                            dash_lname_txt.setEnabled(false);
                            dash_old_password_jpf.setEnabled(false);
                            dash_password_jpf.setEnabled(false);
                            dash_confirm_password_jpf.setEnabled(false);

                            Dash_edit_profile_jpa.remove(dash_save_changes_jbtn);
                            Dash_edit_profile_jpa.updateUI();
                            Dash_edit_profile_jpa.add(dash_edit_profile_jbtn);
                            Dash_edit_profile_jpa.updateUI();
                        }
                        else{
                            JOptionPane.showMessageDialog(MainFrame, "An Error Occured", "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } 
                catch (SQLException ex) 
                {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(MainFrame, ex, "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(MainFrame, e, "Error Message", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
                }
            }
            else {
                // Check if old password input is equal to old password in DB
                if(!(dash_old_password_jpf.getText().equals(logged_in_password))){
                    JOptionPane.showMessageDialog(MainFrame, "Old password doesn't match", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                // Check if confirm password is equal to new password
                else if(!(dash_confirm_password_jpf.getText().equals(dash_password_jpf.getText()))){
                    JOptionPane.showMessageDialog(MainFrame, "Confirm password doesn't match", "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    try 
                    {
                        Class.forName("com.mysql.jdbc.Driver");

                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                        {

                            String response = "";
                            // Update values based on new password value
                            synchronized (this){
                                response = updateValue(fname, lname, password, email);
                            }
                            if(response.equals("Successful")){
                                JOptionPane.showMessageDialog(MainFrame, "Changes Saved");

                                logged_in_fname = fname;
                                logged_in_lname = lname;
                                logged_in_password = password;

                                dash_fname_txt.setText(logged_in_fname);
                                dash_lname_txt.setText(logged_in_lname);

                                dash_old_password_jpf.setText(null);
                                dash_password_jpf.setText(null);
                                dash_confirm_password_jpf.setText(null);
                                
                                dash_fname_txt.setEnabled(false);
                                dash_lname_txt.setEnabled(false);
                                dash_old_password_jpf.setEnabled(false);
                                dash_password_jpf.setEnabled(false);
                                dash_confirm_password_jpf.setEnabled(false);
                                
                                Dash_edit_profile_jpa.remove(dash_save_changes_jbtn);
                                Dash_edit_profile_jpa.updateUI();
                                Dash_edit_profile_jpa.add(dash_edit_profile_jbtn);
                                Dash_edit_profile_jpa.updateUI();
                            }
                            else{
                                JOptionPane.showMessageDialog(MainFrame, "An Error Occured", "Error Message", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(MainFrame, ex, "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(MainFrame, e, "Error Message", JOptionPane.ERROR_MESSAGE);
                        System.out.println(e);
                    }
                }
            }
        }
    }
    
    // Action performed when logout button is pressed
    private void logoutButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            
            logged_in_id = null;
            logged_in_fname = null;
            logged_in_lname = null;
            logged_in_email = null;
            logged_in_password = null;
            
            log_email_txt.setText(null);
            log_password_jpf.setText(null);
            fname_txt.setText(null);
            lname_txt.setText(null);
            reg_email_txt.setText(null);
            reg_password_jpf.setText(null);
            confirm_password_jpf.setText(null);
            
            Layer_jpa.updateUI();
            Layer_jpa.remove(Dashboard_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Home_jpa);
            Layer_jpa.updateUI();
        }
    }
    
    // Action performed when back button is pressed in Question Panel
    private void question_back_to_dashboardButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            
            int option = JOptionPane.showConfirmDialog(MainFrame, "Want to go back ?" + "\n" + "Question form will be cleared !", "Warning Message", JOptionPane.OK_CANCEL_OPTION);
            
            switch (option) {
                case 0:
                    new_question_jta.setText(null);
                    option_1_jta.setText(null);
                    option_2_jta.setText(null);
                    option_3_jta.setText(null);
                    option_4_jta.setText(null);
                    answer_jcb.setSelectedIndex(0);
                    
                    Layer_jpa.updateUI();
                    Layer_jpa.remove(Question_jpa);
                    Layer_jpa.updateUI();
                    Layer_jpa.add(Dashboard_jpa);
                    Layer_jpa.updateUI();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
    
    // Action performed when back button is pressed in Edit Question Panel
    private void edit_question_back_to_dashboardButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            
            int option = JOptionPane.showConfirmDialog(MainFrame, "Want to go back ?" + "\n" + "Question form will be cleared !", "Warning Message", JOptionPane.OK_CANCEL_OPTION);
            
            switch (option) {
                case 0:
                    edit_new_question_jta.setText(null);
                    edit_option_1_jta.setText(null);
                    edit_option_2_jta.setText(null);
                    edit_option_3_jta.setText(null);
                    edit_option_4_jta.setText(null);
                    edit_answer_jcb.setSelectedIndex(0);
                    
                    Layer_jpa.updateUI();
                    Layer_jpa.remove(Edit_Question_jpa);
                    Layer_jpa.updateUI();
                    Layer_jpa.add(AllQuiz_jpa);
                    Layer_jpa.updateUI();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
    
    // Action performed when back button is pressed in All Quiz Panel
    private void allQuiz_back_to_dashboardButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            Layer_jpa.updateUI();
            Layer_jpa.remove(AllQuiz_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Dashboard_jpa);
            Layer_jpa.updateUI();
        }
    }
    
    // Action for each Quiz Question right clicked in the table for further options
    private void mouseRightClick(MouseEvent e){
        if (allQuiz_tbl.getSelectedRowCount() > 0 && e.getButton() == MouseEvent.BUTTON3) {
            right_click_jmu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    // Reload / Load all questions to update values
    private void reload_questions() {
        synchronized(this) {
            try 
            {
                String str = "SELECT * FROM questions ORDER BY added_date DESC";

                Class.forName("com.mysql.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                        Statement stmt = con.createStatement();) 
                {
                    ResultSet rs = stmt.executeQuery(str);
                    
                    dtm_allQuiz.setRowCount(0);
                    
                    while (rs.next()) 
                    {
                        String question_id = rs.getString("id") + "         " ;
                        String admin_id = rs.getString("admin_id") + "                   " ;
                        String question = rs.getString("question");
                        String option1 = rs.getString("option1") + "         " ;
                        String option2 = rs.getString("option2") + "         " ;
                        String option3 = rs.getString("option3") + "         " ;
                        String option4 = rs.getString("option4") + "         " ;
                        String answer = rs.getString("answer") + "         " ;
                        String date = rs.getString("added_date");
                        
                        allQuiz_obj = new Object [] {
                            question_id, admin_id, question, option1, option2, option3, option4, answer, date
                        };

                        dtm_allQuiz.addRow(allQuiz_obj);
                        allQuiz_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                        for (int column = 0; column < allQuiz_tbl.getColumnCount(); column++){
                            TableColumn tableColumn = allQuiz_tbl.getColumnModel().getColumn(column);
                            int preferredWidth = tableColumn.getMinWidth();
                            int maxWidth = tableColumn.getMaxWidth();

                            for (int row = 0; row < allQuiz_tbl.getRowCount(); row++)
                            {
                                TableCellRenderer cellRenderer = allQuiz_tbl.getCellRenderer(row, column);
                                Component c = allQuiz_tbl.prepareRenderer(cellRenderer, row, column);
                                int width = c.getPreferredSize().width + allQuiz_tbl.getIntercellSpacing().width;
                                preferredWidth = Math.max(preferredWidth, width);

                                //  We've exceeded the maximum width, no need to check other rows
                                
                                if (preferredWidth >= maxWidth)
                                {
                                    preferredWidth = maxWidth;
                                    break;
                                }
                            }

                            tableColumn.setPreferredWidth( preferredWidth );
                        }
                        
                    }

                }
            } 
            catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editButtonActionPerformed(ActionEvent evt){
        synchronized(this){
            if (allQuiz_tbl.getRowCount() > 0) {
                if (allQuiz_tbl.getSelectedRowCount() > 0) {
                    int selectedRow[] = allQuiz_tbl.getSelectedRows();
                    for (int i : selectedRow) {
                        String question_id  = allQuiz_tbl.getValueAt(i, 0).toString();
                        
                        try 
                        {
                            String str = "SELECT * FROM questions WHERE id = ? ";

                            Class.forName("com.mysql.jdbc.Driver");

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                            {
                                sql_stat = con.prepareStatement(str);

                                sql_stat.setString(1, question_id);

                                ResultSet rs = sql_stat.executeQuery();
                                
                                while (rs.next()) 
                                {
                                    String edit_question_id = rs.getString("id");
                                    String question = rs.getString("question");
                                    String option1 = rs.getString("option1");
                                    String option2 = rs.getString("option2");
                                    String option3 = rs.getString("option3");
                                    String option4 = rs.getString("option4");
                                    String answer = rs.getString("answer");
                                    
                                    global_edit_question_id = edit_question_id;
                                    edit_new_question_jta.setText(question);
                                    edit_option_1_jta.setText(option1);
                                    edit_option_2_jta.setText(option2);
                                    edit_option_3_jta.setText(option3);
                                    edit_option_4_jta.setText(option4);
                                    edit_answer_jcb.setSelectedItem(answer);
                                    
                                    synchronized(this){
                                        Layer_jpa.updateUI();
                                        Layer_jpa.remove(AllQuiz_jpa);
                                        Layer_jpa.updateUI();
                                        Layer_jpa.add(Edit_Question_jpa);
                                        Layer_jpa.updateUI();
                                    }
                                }
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            System.out.println(ex);
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
    
    private void allQuizRefreshButtonActionPerformed(ActionEvent evt){
        reload_questions();
    }
    
    private void deleteButtonActionPerformed(ActionEvent evt){
        int option = JOptionPane.showConfirmDialog(MainFrame, "Do you really want to delete ?", "Question Message", JOptionPane.YES_NO_OPTION);
        if(option == 0){
            synchronized(this){
                if (allQuiz_tbl.getRowCount() > 0) {
                    if (allQuiz_tbl.getSelectedRowCount() > 0) {
                        int selectedRow[] = allQuiz_tbl.getSelectedRows();
                        for (int i : selectedRow) {
                            String question_id  = allQuiz_tbl.getValueAt(i, 0).toString();

                            try 
                            {
                                String str = "DELETE FROM questions WHERE id = ? ";

                                Class.forName("com.mysql.jdbc.Driver");

                                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) 
                                {
                                    sql_stat = con.prepareStatement(str);

                                    sql_stat.setString(1, question_id);

                                    sql_stat.execute();

                                    JOptionPane.showMessageDialog(MainFrame, "Deleted ...");
                                    reload_questions();
                                }
                            } 
                            catch (SQLException ex) 
                            {
                                JOptionPane.showMessageDialog(MainFrame, ex, "Error Message", JOptionPane.ERROR_MESSAGE);
//                                System.out.println(ex);
                            }
                            catch (Exception e){
                                JOptionPane.showMessageDialog(MainFrame, e, "Error Message", JOptionPane.ERROR_MESSAGE);
//                                System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(MainFrame, "Delete Cancelled", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        
        new Admin_QuizApp();

    }
}
