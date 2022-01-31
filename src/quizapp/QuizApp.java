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
public class QuizApp {

    // Declaring all Components to be used
    // All Frames
    JFrame MainFrame;

    // All Panels
    JPanel Layer_jpa, Home_jpa, Login_jpa, Register_jpa, Dashboard_jpa, Question_jpa, Result_jpa, Dash_edit_profile_jpa;

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
    JLabel dashboard_lbl, dash_welcome_lbl, take_quiz_lbl, show_results_lbl, edit_profile_lbl,
            dash_fname_lbl, dash_lname_lbl, dash_confirm_password_lbl, dash_email_lbl, dash_password_lbl,
            dash_old_password_lbl;

    // Dashboard TextFields
    JTextField dash_email_txt, dash_fname_txt, dash_lname_txt;

    // Dashboard Password Fields
    JPasswordField dash_password_jpf, dash_old_password_jpf, dash_confirm_password_jpf;

    // Dashboard Buttons
    JButton logout_jbtn, dash_edit_profile_jbtn, dash_save_changes_jbtn, take_quiz_jbtn, show_results_jbtn;

    // Question Labels
    JLabel question_lbl, question_count_lbl, question_o_lbl;

    // Question TextFields
    // Question RadioButtons
    JRadioButton option_1_jrb, option_2_jrb, option_3_jrb, option_4_jrb;

    // Question Buttons
    JButton question_back_to_dashboard_jbtn, previous_question_jbtn, next_question_jbtn, save_progress_jbtn;

    // Question Button Group
    ButtonGroup answer_bg;

    // Result Labels
    JLabel result_lbl;

    // Result TextFields
    // Result ScrollPanes
    JScrollPane results_scroll;

    // AllQuiz Table
    JTable results_tbl;

    // AllQuiz DTM
    DefaultTableModel dtm_results;

    // AllQuiz objects
    Object[] results_obj;

    // Result Buttons
    JButton results_back_to_dashboard_jbtn, results_refresh_jbtn;

    // Menu bar
    JMenuBar menubar_jmb;
    // Menus
    JMenu connection_jmu;
    // MenuItem
    JMenuItem refresh_jmi;

    // Used to keep track of the database connection
    private boolean connection = true;

    // Used to check if there are any users currently in database
    private String users = "";

    // SQL Components
    Connection sql_con;
    Statement sql_stmt;
    PreparedStatement sql_stat;
    ResultSet rs;

    private String logged_in_id;
    private String logged_in_fname;
    private String logged_in_lname;
    private String logged_in_email;
    private String logged_in_password;

    private ArrayList<String> global_question_id = new ArrayList<>();
    private ArrayList<String> global_question = new ArrayList<>();
    private ArrayList<String> global_option_1 = new ArrayList<>();
    private ArrayList<String> global_option_2 = new ArrayList<>();
    private ArrayList<String> global_option_3 = new ArrayList<>();
    private ArrayList<String> global_option_4 = new ArrayList<>();
    private ArrayList<String> global_answer = new ArrayList<>();

    private ArrayList<String> selected_answers = new ArrayList<>();

    private int global_question_count = 0;

    private String selected_answer;

    public QuizApp() {
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
        Home_jpa.setBackground(new java.awt.Color(102, 0, 0));
        Home_jpa.setBounds(0, 0, 1100, 700);

        // Home - Labels
        welcome_lbl = new JLabel();
        welcome_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quizapp/images/Network 2.PNG")));

        // Home - Buttons
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);

        // Home - Sub - Panels
        // -----------------------------------------
        Login_jpa = new JPanel();
        Login_jpa.setBackground(new java.awt.Color(102, 0, 0));

        // Login - Labels
        email_lbl = new JLabel("Email");
        email_lbl.setForeground(Color.WHITE);
        email_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        password_lbl = new JLabel("Password");
        password_lbl.setForeground(Color.WHITE);
        password_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        or_lbl = new JLabel("or");
        or_lbl.setForeground(Color.blue);
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
            public void keyTyped(KeyEvent e) {
                Character c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        // Login Layout
        Login_jpa.setLayout(null);

        // Login Panels - attr
        Login_jpa.setBounds(630, 160, 400, 300);

        // Login Default Label
        login_lbl = new JLabel("Login");

        // Login Default Label - attr
        login_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        login_lbl.setForeground(Color.WHITE);

        // Login components - attr
        login_lbl.setBounds(10, 0, 200, 40);

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
        Register_jpa.setBackground(new java.awt.Color(102, 0, 0));

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
        or_lbl.setForeground(Color.blue);
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
        register_lbl = new JLabel("Register");

        // Register Default Label - attr
        register_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        register_lbl.setForeground(Color.WHITE);

        // Register components - attr
        register_lbl.setBounds(10, 0, 200, 40);

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
        home_lbl = new JLabel("Home");

        // Home Default Label - attr
        home_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        home_lbl.setForeground(Color.WHITE);

        // Home components - attr
        home_lbl.setBounds(10, 20, 200, 40);

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
        Dashboard_jpa.setBackground(new java.awt.Color(102, 0, 0));
        Dashboard_jpa.setBounds(0, 0, 1100, 700);

        // Dashboard - Labels
        dash_welcome_lbl = new JLabel();
        dash_welcome_lbl.setFont(new Font("Serif", Font.PLAIN, 25));
        dash_welcome_lbl.setForeground(Color.WHITE);
        take_quiz_lbl = new JLabel("Take Quiz");
        take_quiz_lbl.setFont(new Font("Arial", Font.PLAIN, 30));
        take_quiz_lbl.setForeground(Color.WHITE);
        show_results_lbl = new JLabel("Show Results");
        show_results_lbl.setFont(new Font("Arial", Font.PLAIN, 30));
        show_results_lbl.setForeground(Color.WHITE);

        // Dashboard - Buttons
        logout_jbtn = new JButton("Logout");
        logout_jbtn.setCursor(cur);
        logout_jbtn.setBackground(new java.awt.Color(102, 0, 0));
        logout_jbtn.setForeground(Color.WHITE);
        logout_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        logout_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        logout_jbtn.setEnabled(connection);

        take_quiz_jbtn = new JButton("Quiz");
        take_quiz_jbtn.setCursor(cur);
        take_quiz_jbtn.setBackground(Color.blue);
        take_quiz_jbtn.setForeground(Color.WHITE);
        take_quiz_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        take_quiz_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                takeQuizButtonActionPerformed(evt);
            }
        });
        take_quiz_jbtn.setEnabled(connection);

        show_results_jbtn = new JButton("Results");
        show_results_jbtn.setCursor(cur);
        show_results_jbtn.setBackground(Color.green);
        show_results_jbtn.setForeground(Color.BLACK);
        show_results_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        show_results_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                showResultButtonActionPerformed(evt);
            }
        });
        show_results_jbtn.setEnabled(connection);

        // Dashboard - Sub - Panels
        // -----------------------------------------
        Dash_edit_profile_jpa = new JPanel();
        Dash_edit_profile_jpa.setBackground(new java.awt.Color(102, 0, 0));

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
        edit_profile_lbl = new JLabel("Edit Profile");

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
        dashboard_lbl = new JLabel("Dashboard");

        // Dashboard Default Label - attr
        dashboard_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        dashboard_lbl.setForeground(Color.WHITE);

        // Dashboard components - attr
        dashboard_lbl.setBounds(10, 20, 200, 40);

        // Dashboard Labels - attr
        dash_welcome_lbl.setBounds(10, 90, 400, 40);
        take_quiz_lbl.setBounds(50, 240, 200, 40);
        show_results_lbl.setBounds(50, 320, 200, 40);

        // Dashboard TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        logout_jbtn.setBounds(950, 30, 100, 30);
        take_quiz_jbtn.setBounds(260, 240, 100, 40);
        show_results_jbtn.setBounds(260, 320, 100, 40);

        // Adding all Dashboard Panel variables
        Dashboard_jpa.add(dashboard_lbl);
        Dashboard_jpa.add(logout_jbtn);
        Dashboard_jpa.add(dash_welcome_lbl);
        Dashboard_jpa.add(take_quiz_lbl);
        Dashboard_jpa.add(take_quiz_jbtn);
        Dashboard_jpa.add(show_results_lbl);
        Dashboard_jpa.add(show_results_jbtn);
        Dashboard_jpa.add(Dash_edit_profile_jpa);

        // Dashboard Layout - attr
        Dashboard_jpa.setLayout(null);

        // -------------------------------------------------------------------------
        // Initializing the Question Panel
        Question_jpa = new JPanel();
        Question_jpa.setBackground(new java.awt.Color(102, 0, 0));
        Question_jpa.setBounds(0, 0, 1100, 700);

        // Question - Labels
        question_count_lbl = new JLabel();
        question_count_lbl.setForeground(Color.WHITE);
        question_count_lbl.setFont(new Font("Arial", Font.PLAIN, 20));
        question_o_lbl = new JLabel();
        question_o_lbl.setForeground(Color.WHITE);
        question_o_lbl.setFont(new Font("Arial", Font.PLAIN, 20));

        // Question - Buttons
        question_back_to_dashboard_jbtn = new JButton("Back");
        question_back_to_dashboard_jbtn.setCursor(cur);
        question_back_to_dashboard_jbtn.setBackground(new java.awt.Color(102, 0, 0));
        question_back_to_dashboard_jbtn.setForeground(Color.WHITE);
        question_back_to_dashboard_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        question_back_to_dashboard_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                question_back_to_dashboardButtonActionPerformed(evt);
            }
        });
        question_back_to_dashboard_jbtn.setEnabled(connection);

        save_progress_jbtn = new JButton("Save");
        save_progress_jbtn.setCursor(cur);
        save_progress_jbtn.setBackground(Color.green);
        save_progress_jbtn.setForeground(Color.BLACK);
        save_progress_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        save_progress_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                save_progressButtonActionPerformed(evt);
            }
        });
        save_progress_jbtn.setEnabled(connection);

        next_question_jbtn = new JButton("Next");
        next_question_jbtn.setCursor(cur);
        next_question_jbtn.setBackground(Color.blue);
        next_question_jbtn.setForeground(Color.WHITE);
        next_question_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        next_question_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                next_questionButtonActionPerformed(evt);
            }
        });
        next_question_jbtn.setEnabled(connection);

        previous_question_jbtn = new JButton("Previous");
        previous_question_jbtn.setCursor(cur);
        previous_question_jbtn.setBackground(new java.awt.Color(102, 0, 0));
        previous_question_jbtn.setForeground(Color.WHITE);
        previous_question_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        previous_question_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous_questionButtonActionPerformed(evt);
            }
        });
        previous_question_jbtn.setEnabled(connection);

        // Question RadioButtons
        option_1_jrb = new JRadioButton();
        option_1_jrb.setForeground(Color.WHITE);
        option_1_jrb.setFont(new Font("Arial", Font.PLAIN, 18));
        option_1_jrb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                optionSelectedButtonActionPerformed(evt);
            }
        });
        option_2_jrb = new JRadioButton();
        option_2_jrb.setForeground(Color.WHITE);
        option_2_jrb.setFont(new Font("Arial", Font.PLAIN, 18));
        option_2_jrb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                optionSelectedButtonActionPerformed(evt);
            }
        });
        option_3_jrb = new JRadioButton();
        option_3_jrb.setForeground(Color.WHITE);
        option_3_jrb.setFont(new Font("Arial", Font.PLAIN, 18));
        option_3_jrb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                optionSelectedButtonActionPerformed(evt);
            }
        });
        option_4_jrb = new JRadioButton();
        option_4_jrb.setForeground(Color.WHITE);
        option_4_jrb.setFont(new Font("Arial", Font.PLAIN, 18));
        option_4_jrb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                optionSelectedButtonActionPerformed(evt);
            }
        });

        // Question ButtonGroups
        answer_bg = new ButtonGroup();
        answer_bg.add(option_1_jrb);
        answer_bg.add(option_2_jrb);
        answer_bg.add(option_3_jrb);
        answer_bg.add(option_4_jrb);

        // Question Layout
        Question_jpa.setLayout(null);

        // Question Panels - attr
        // Question Default Label
        question_lbl = new JLabel("Quiz Questions");

        // Question Default Label - attr
        question_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        question_lbl.setForeground(Color.WHITE);

        // Question components - attr
        question_lbl.setBounds(10, 20, 300, 40);

        // Question Labels - attr
        // Question TextFields / TextAreas / ComboBox(es) / Buttons / RadioButtons / Lists / ScrollPanes - attr
        question_o_lbl.setBounds(50, 100, 900, 30);
        option_1_jrb.setBounds(90, 185, 900, 20);
        option_2_jrb.setBounds(90, 285, 900, 20);
        option_3_jrb.setBounds(90, 385, 900, 20);
        option_4_jrb.setBounds(90, 485, 900, 20);
        question_count_lbl.setBounds(850, 30, 70, 30);
        question_back_to_dashboard_jbtn.setBounds(950, 30, 100, 30);
        next_question_jbtn.setBounds(950, 550, 100, 30);
        save_progress_jbtn.setBounds(950, 550, 100, 30);

        // Adding all Question Panel variables
        Question_jpa.add(question_lbl);
        Question_jpa.add(question_count_lbl);
        Question_jpa.add(question_o_lbl);
        Question_jpa.add(option_1_jrb);
        Question_jpa.add(option_2_jrb);
        Question_jpa.add(option_3_jrb);
        Question_jpa.add(option_4_jrb);
        Question_jpa.add(question_back_to_dashboard_jbtn);
        Question_jpa.add(next_question_jbtn);

        // Question Layout - attr
        Question_jpa.setLayout(null);

        // -------------------------------------------------------------------------
        // Initializing the Result Panel
        Result_jpa = new JPanel();
        Result_jpa.setBackground(new java.awt.Color(102, 0, 0));
        Result_jpa.setBounds(0, 0, 1100, 700);

        // Result - Labels
        // Result - Buttons
        results_back_to_dashboard_jbtn = new JButton("Back");
        results_back_to_dashboard_jbtn.setCursor(cur);
        results_back_to_dashboard_jbtn.setBackground(new java.awt.Color(102, 0, 0));
        results_back_to_dashboard_jbtn.setForeground(Color.WHITE);
        results_back_to_dashboard_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        results_back_to_dashboard_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                results_back_to_dashboardButtonActionPerformed(evt);
            }
        });
        results_back_to_dashboard_jbtn.setEnabled(connection);

        results_refresh_jbtn = new JButton("Refresh");
        results_refresh_jbtn.setCursor(cur);
        results_refresh_jbtn.setBackground(new java.awt.Color(102, 0, 0));
        results_refresh_jbtn.setForeground(Color.WHITE);
        results_refresh_jbtn.setFont(new Font("Arial", Font.PLAIN, 17));
        results_refresh_jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                resultsRefreshButtonActionPerformed(evt);
            }
        });
        results_refresh_jbtn.setEnabled(connection);

        // Results Lists / DefaultlistModels / Scrolls / Tables
        results_tbl = new JTable();
        results_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        dtm_results = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        results_tbl.setModel(dtm_results);
        results_scroll = new JScrollPane();
        results_scroll.setViewportView(results_tbl);

        // Result Layout
        Result_jpa.setLayout(null);

        // Result Panels - attr
        // Result Default Label
        result_lbl = new JLabel("Results");

        // Result Default Label - attr
        result_lbl.setFont(new Font("Serif", Font.PLAIN, 35));
        result_lbl.setForeground(Color.WHITE);

        // Result components - attr
        result_lbl.setBounds(10, 20, 200, 40);

        // Result Labels - attr
        // AllQuiz Table - attr
        dtm_results.addColumn("Email");
        dtm_results.addColumn("Result");
        dtm_results.addColumn("Date");

        results_tbl.getTableHeader().setReorderingAllowed(false);
        results_tbl.getTableHeader().setResizingAllowed(false);

        // Result TextFields / ComboBox(es) / Buttons / Lists / ScrollPanes - attr
        results_refresh_jbtn.setBounds(830, 30, 100, 30);
        results_back_to_dashboard_jbtn.setBounds(950, 30, 100, 30);
        results_scroll.setBounds(360, 180, 370, 300); // after adding the member panel you put the panel in the scrollpane then set bounds for the scrollpane

        // Adding all Result Panel variables
        Result_jpa.add(result_lbl);
        Result_jpa.add(results_refresh_jbtn);
        Result_jpa.add(results_scroll);
        Result_jpa.add(results_back_to_dashboard_jbtn);

        // Result Layout - attr
        Result_jpa.setLayout(null);

        // -------------------------------------------------------------------------
        // Adding Home Panel to Layer Panel
        Layer_jpa.add(Home_jpa);

        MainFrame.add(Layer_jpa);

        MainFrame.setSize(1100, 700);
        MainFrame.setTitle("Quiz App");
        MainFrame.setVisible(true);
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setResizable(false);
        MainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/quizapp/images/Icon 2.PNG")));
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // Setting up SQL Connection
    private void setConnection() {
        // Setup SQL connection
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            sql_con = DriverManager.getConnection("jdbc:mysql://localhost:3307/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
            sql_stmt = sql_con.createStatement();

            // clear everything here
            connection = true;

            // enable everything here (If connection successful)
            logout_jbtn.setEnabled(connection);
            take_quiz_jbtn.setEnabled(connection);
            show_results_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            next_question_jbtn.setEnabled(connection);
            previous_question_jbtn.setEnabled(connection);
            save_progress_jbtn.setEnabled(connection);
            results_back_to_dashboard_jbtn.setEnabled(connection);
            results_refresh_jbtn.setEnabled(connection);

            JOptionPane.showMessageDialog(MainFrame, "Connected", "Connection Successful", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalStateException ex) {
            // If connection not successful disable all buttons
            connection = false;

            logout_jbtn.setEnabled(connection);
            take_quiz_jbtn.setEnabled(connection);
            show_results_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            next_question_jbtn.setEnabled(connection);
            previous_question_jbtn.setEnabled(connection);
            save_progress_jbtn.setEnabled(connection);
            results_back_to_dashboard_jbtn.setEnabled(connection);
            results_refresh_jbtn.setEnabled(connection);

            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Connection Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            // If connection not successful disable all buttons
            connection = false;

            logout_jbtn.setEnabled(connection);
            take_quiz_jbtn.setEnabled(connection);
            show_results_jbtn.setEnabled(connection);
            dash_edit_profile_jbtn.setEnabled(connection);
            dash_save_changes_jbtn.setEnabled(connection);
            login_jbtn.setEnabled(connection);
            register_jbtn.setEnabled(connection);
            goto_login_jbtn.setEnabled(connection);
            goto_register_jbtn.setEnabled(connection);
            question_back_to_dashboard_jbtn.setEnabled(connection);
            next_question_jbtn.setEnabled(connection);
            previous_question_jbtn.setEnabled(connection);
            save_progress_jbtn.setEnabled(connection);
            results_back_to_dashboard_jbtn.setEnabled(connection);
            results_refresh_jbtn.setEnabled(connection);

            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Connection Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    // Check if email exist before reistering
    private String reg_check_email_exist(String email) {
        String Email = email;

        synchronized (this) {
            try {
                String str = "SELECT * FROM users";

                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                        Statement stmt = con.createStatement();) {
                    ResultSet rs = stmt.executeQuery(str);

                    while (rs.next()) {
                        String db_email = rs.getString("email");
                        if (Email.equalsIgnoreCase(db_email)) {
                            Email = "email_exists";
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
        return Email;
    }

    private void login() {
        if (connection) {
            synchronized (this) {
                String email = log_email_txt.getText();
                String password = log_password_jpf.getText();
                String email_check = check_email(email);
                String email_not_exist = log_check_email_exist(email);

                if (log_email_txt.getText().equals("") || log_password_jpf.getText().equals("")) {
                    JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (email_check.equals("email_error")) {
                        JOptionPane.showMessageDialog(MainFrame, "Incorrect email", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else if (email_not_exist.equals("not_exist")) {
                        JOptionPane.showMessageDialog(MainFrame, "Email does not exist", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } else {

                        try {
                            String str = "SELECT * FROM users WHERE email = ?";

                            Class.forName("com.mysql.cj.jdbc.Driver");

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) {
                                sql_stat = con.prepareStatement(str);

                                sql_stat.setString(1, email);

                                ResultSet rs = sql_stat.executeQuery();

                                while (rs.next()) {
                                    String db_pword = rs.getString("password");
                                    if (password.equalsIgnoreCase(db_pword)) {
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
                                    } else {
                                        JOptionPane.showMessageDialog(MainFrame, "Incorrect password", "Warning Message", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(MainFrame, "Logged in successfully", "Success message", JOptionPane.PLAIN_MESSAGE);
//                                        Layer_jpa.updateUI();
//                                        Layer_jpa.remove(Home_jpa);
//                                        Layer_jpa.updateUI();
//                                        Layer_jpa.add(Dashboard_jpa);
//                                        Layer_jpa.updateUI();
        }
    }

    // Check if there is any user in the DB at all before logging in or registering
    private void check_for_users() {
        if (connection) {

            synchronized (this) {
                try {
                    String str = "SELECT * FROM admin";

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                            Statement stmt = con.createStatement();) {
                        ResultSet rs = stmt.executeQuery(str);

                        if (!(rs.next())) {
                            users = "no";
                        } else {
                            users = "yes";
                        }

                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error: No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Check if email exist before logging in
    private String log_check_email_exist(String email) {
        check_for_users();
        String Email = "";
        if (connection) {
            if (users.equals("no")) {
                JOptionPane.showMessageDialog(MainFrame, "No users yet, Sign up ...", "Warning Message", JOptionPane.WARNING_MESSAGE);
            } else {

                synchronized (this) {
                    try {
                        String str = "SELECT * FROM users";

                        Class.forName("com.mysql.cj.jdbc.Driver");

                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                                Statement stmt = con.createStatement();) {
                            ResultSet rs = stmt.executeQuery(str);

                            while (rs.next()) {
                                String db_email = rs.getString("email");
                                if (email.equals(db_email)) {
                                    Email = email;
                                    break;
                                } else {
                                    Email = "not_exist";
                                }
                            }

                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error: No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        return Email;
    }

    // Check for correct email
    private String check_email(String email) {
        String Email = email;

        Pattern email_pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher email_macther = email_pattern.matcher(Email);

        boolean email_boolean = email_macther.matches();

        if (email_boolean) {
            return Email;
        } else {
            return "email_error";
        }
    }

    // Getting all the questions to be answered from DB
    private void getAllQuestions() {
        if (connection) {
            synchronized (this) {
                try {
                    String str = "SELECT * FROM questions ORDER BY added_date DESC";

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");
                            Statement stmt = con.createStatement();) {
                        ResultSet rs = stmt.executeQuery(str);

                        while (rs.next()) {
                            String question_id = rs.getString("id");
                            String question = rs.getString("question");
                            String option1 = rs.getString("option1");
                            String option2 = rs.getString("option2");
                            String option3 = rs.getString("option3");
                            String option4 = rs.getString("option4");
                            String answer = rs.getString("answer");

                            global_question_id.add(question_id);
                            global_question.add(question);
                            global_option_1.add(option1);
                            global_option_2.add(option2);
                            global_option_3.add(option3);
                            global_option_4.add(option4);
                            global_answer.add(answer);

                        }

                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error: No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Show panel for answering questions and adding the questions to the respecteed fields
    private void addQuestions() {

        String question = global_question.get(global_question_count);
        String option1 = global_option_1.get(global_question_count);
        String option2 = global_option_2.get(global_question_count);
        String option3 = global_option_3.get(global_question_count);
        String option4 = global_option_4.get(global_question_count);
        int count = global_question_count + 1;

        question_o_lbl.setText(question);
        option_1_jrb.setText(option1);
        option_2_jrb.setText(option2);
        option_3_jrb.setText(option3);
        option_4_jrb.setText(option4);
        question_count_lbl.setText(String.valueOf(count));

    }

    // Reload / Load all results to update values
    private void reload_results() {
        if (connection) {
            synchronized (this) {
                try {
                    String str = "SELECT * FROM results WHERE user_email = ? ORDER BY added_date DESC";

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) {
                        sql_stat = con.prepareStatement(str);

                        sql_stat.setString(1, logged_in_email);

                        ResultSet rs = sql_stat.executeQuery();
                        dtm_results.setRowCount(0);

//                        if(!(rs.next())){
//                            JOptionPane.showMessageDialog(MainFrame, "No results yet, go take the quiz ...", "Message", JOptionPane.PLAIN_MESSAGE);
//                        }
//                        else {
                        while (rs.next()) {
                            String email = rs.getString("user_email") + "         ";
                            String results = rs.getString("percentage") + "                   ";
                            String date = rs.getString("added_date");

                            results_obj = new Object[]{
                                email, results, date
                            };

                            dtm_results.addRow(results_obj);
                            results_tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                            for (int column = 0; column < results_tbl.getColumnCount(); column++) {
                                TableColumn tableColumn = results_tbl.getColumnModel().getColumn(column);
                                int preferredWidth = tableColumn.getMinWidth();
                                int maxWidth = tableColumn.getMaxWidth();

                                for (int row = 0; row < results_tbl.getRowCount(); row++) {
                                    TableCellRenderer cellRenderer = results_tbl.getCellRenderer(row, column);
                                    Component c = results_tbl.prepareRenderer(cellRenderer, row, column);
                                    int width = c.getPreferredSize().width + results_tbl.getIntercellSpacing().width;
                                    preferredWidth = Math.max(preferredWidth, width);

                                    //  We've exceeded the maximum width, no need to check other rows
                                    if (preferredWidth >= maxWidth) {
                                        preferredWidth = maxWidth;
                                        break;
                                    }
                                }

                                tableColumn.setPreferredWidth(preferredWidth);
                            }

                        }
//                            sql_stat.clearParameters();
//                        }

                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame, "SQL Error:" + ex.getMessage() + "\nCode : " + ex.getErrorCode(), "Error Message", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error: No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresh / Retry connection
    private void refreshButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            JOptionPane.showMessageDialog(MainFrame, "Connecting ...", "Information Message", JOptionPane.INFORMATION_MESSAGE);
            // Resetting SQL connection

            setConnection();
        }
    }

    // Logging in new users
    private void loginButtonActionPerformed(ActionEvent evt) {
        login();
    }

    // Choose to login
    private void go_to_registerButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            Home_jpa.updateUI();
            Home_jpa.remove(Login_jpa);
            Home_jpa.updateUI();
            Home_jpa.add(Register_jpa);
            Home_jpa.updateUI();
        }
    }

    // Registering new users
    private void registerButtonActionPerformed(ActionEvent evt) {
        if (connection) {
            synchronized (this) {
                String email_exist = reg_check_email_exist(reg_email_txt.getText());
                String email_check = check_email(reg_email_txt.getText());
                try {
                    if (fname_txt.getText().equals("") || lname_txt.getText().equals("") || reg_email_txt.getText().equals("")
                            || reg_password_jpf.getText().equals("") || confirm_password_jpf.getText().equals("")) {
                        JOptionPane.showMessageDialog(MainFrame, "Fill in all fields", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else {

                        if (email_exist.equals("email_exists")) {
                            JOptionPane.showMessageDialog(MainFrame, "Email already exists, Going to Login ...", "Error Message", JOptionPane.ERROR_MESSAGE);
                            Home_jpa.updateUI();
                            Home_jpa.remove(Register_jpa);
                            Home_jpa.updateUI();
                            Home_jpa.add(Login_jpa);
                            Home_jpa.updateUI();

                            log_email_txt.setText(reg_email_txt.getText());
                        } else {

                            if (email_check.equals("email_error")) {
                                JOptionPane.showMessageDialog(MainFrame, "Incorrect email", "Warning Message", JOptionPane.WARNING_MESSAGE);
                            } else {

                                if (reg_password_jpf.getText().equals(confirm_password_jpf.getText())) {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                    Date dateN = new Date();
                                    String Date = dateFormat.format(dateN);

                                    String first_name = fname_txt.getText();
                                    String last_name = lname_txt.getText();
                                    String email = email_exist;
                                    String password = reg_password_jpf.getText();

                                    // Inserting new registered members in DB (using prepared statement)
                                    sql_stat = sql_con.prepareStatement("INSERT INTO users (firstName, lastName, email, password, added_date) VALUES(?, ?, ?, ?, ?)");

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
                                } else {
                                    JOptionPane.showMessageDialog(MainFrame, "Confirm password doesn't match, try again", "Warning message", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {

                    String msg = ex.getMessage();

                    if (ex.getMessage().equals(msg)) {
                        JOptionPane.showMessageDialog(MainFrame, "SQL Error : " + msg + "\n Code : " + ex.getErrorCode(), "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (Exception e) {
                    String msg = e.getLocalizedMessage();
                    JOptionPane.showMessageDialog(MainFrame, "Error : " + msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Choose to login
    private void go_to_loginButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            Home_jpa.updateUI();
            Home_jpa.remove(Register_jpa);
            Home_jpa.updateUI();
            Home_jpa.add(Login_jpa);
            Home_jpa.updateUI();
        }
    }

    // Action performed when back button is pressed in Question Panel
    private void question_back_to_dashboardButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {

            int option = JOptionPane.showConfirmDialog(MainFrame, "Want to go back ?" + "\n" + "You'll lose all progress !", "Warning Message", JOptionPane.OK_CANCEL_OPTION);

            switch (option) {
                case 0:
                    global_question_id.clear();
                    global_question.clear();
                    global_option_1.clear();
                    global_option_2.clear();
                    global_option_3.clear();
                    global_option_4.clear();
                    global_answer.clear();
                    selected_answers.clear();
                    global_question_count = 0;
                    selected_answer = null;

                    answer_bg.clearSelection();

                    Question_jpa.updateUI();
                    Question_jpa.remove(save_progress_jbtn);
                    Question_jpa.updateUI();
                    Question_jpa.add(next_question_jbtn);
                    Question_jpa.updateUI();

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

    // Action performed when back button is pressed in Results Panel
    private void results_back_to_dashboardButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            dtm_results.setRowCount(0);

            Layer_jpa.updateUI();
            Layer_jpa.remove(Result_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Dashboard_jpa);
            Layer_jpa.updateUI();
        }
    }

    // Starting the quiz on the Question Panel
    private void takeQuizButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            global_question_id.clear();
            global_question.clear();
            global_option_1.clear();
            global_option_2.clear();
            global_option_3.clear();
            global_option_4.clear();
            global_answer.clear();
            selected_answers.clear();
            global_question_count = 0;
            selected_answer = null;

            answer_bg.clearSelection();

            Layer_jpa.updateUI();
            Layer_jpa.remove(Dashboard_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Question_jpa);
            Layer_jpa.updateUI();

            Question_jpa.updateUI();
            Question_jpa.remove(save_progress_jbtn);
            Question_jpa.updateUI();
            Question_jpa.add(next_question_jbtn);
            Question_jpa.updateUI();

            getAllQuestions();
            addQuestions();
        }
    }

    // Refresh users results on Result Panel
    private void resultsRefreshButtonActionPerformed(ActionEvent evt) {
        reload_results();
    }

    // Showing the results so far of a particular user on Result Panel
    private void showResultButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            reload_results();

            Layer_jpa.updateUI();
            Layer_jpa.remove(Dashboard_jpa);
            Layer_jpa.updateUI();
            Layer_jpa.add(Result_jpa);
            Layer_jpa.updateUI();
        }
    }

    // Saving results to DB
    private void save_progressButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            selected_answers.add(selected_answer);
            Iterator answers_itr = selected_answers.iterator();
            Iterator db_answers_itr = global_answer.iterator();

            int correct_count = 0;
            int incorrect_count = 0;

            while (answers_itr.hasNext()) {
                while (db_answers_itr.hasNext()) {
                    if (answers_itr.next().equals(db_answers_itr.next())) {
//                        System.out.println("Correct");
                        correct_count += 1;
                    } else {
//                        System.out.println("Incorrect");
                        incorrect_count += 1;
                    }
                }
            }

            if (connection) {
                synchronized (this) {
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        Date dateN = new Date();
                        String Date = dateFormat.format(dateN);

                        int total = correct_count + incorrect_count;
                        int average = 100 / total;
                        int percentage = correct_count * average;

                        String str_percentage = String.valueOf(percentage) + "%";

                        // Inserting results in DB (using prepared statement)
                        sql_stat = sql_con.prepareStatement("INSERT INTO results (user_email, percentage, added_date) VALUES(?, ?, ?)");

                        sql_stat.setString(1, logged_in_email);
                        sql_stat.setString(2, str_percentage);
                        sql_stat.setString(3, Date);

                        sql_stat.execute();

                        JOptionPane.showMessageDialog(MainFrame, "Result = " + str_percentage, "Quiz Complete", JOptionPane.PLAIN_MESSAGE);
                        Layer_jpa.updateUI();
                        Layer_jpa.remove(Question_jpa);
                        Layer_jpa.updateUI();
                        Layer_jpa.add(Dashboard_jpa);
                        Layer_jpa.updateUI();

                        Question_jpa.updateUI();
                        Question_jpa.remove(save_progress_jbtn);
                        Question_jpa.updateUI();
                        Question_jpa.add(next_question_jbtn);
                        Question_jpa.updateUI();

                    } catch (SQLException ex) {

                        String msg = ex.getMessage();

                        if (ex.getMessage().equals(msg)) {
                            JOptionPane.showMessageDialog(MainFrame, "SQL Error : " + msg + "\n Code : " + ex.getErrorCode(), "Warning Message", JOptionPane.WARNING_MESSAGE);
                        }

                    } catch (Exception e) {
                        String msg = e.getLocalizedMessage();
                        JOptionPane.showMessageDialog(MainFrame, "Error : " + msg, "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(MainFrame, "Error : No connection to database", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // Go to next question after answering the active one
    private void next_questionButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            if (option_1_jrb.isSelected() == false && option_2_jrb.isSelected() == false
                    && option_3_jrb.isSelected() == false && option_4_jrb.isSelected() == false) {
                JOptionPane.showMessageDialog(MainFrame, "Must select an answer !", "Warning Message", JOptionPane.WARNING_MESSAGE);
            } else {
                if (global_question.size() == global_question_count + 1) {
                    Question_jpa.updateUI();
                    Question_jpa.remove(next_question_jbtn);
                    Question_jpa.updateUI();
                    Question_jpa.add(save_progress_jbtn);
                    Question_jpa.updateUI();
                } else {

                    answer_bg.clearSelection();

                    selected_answers.add(selected_answer);

                    global_question_count += 1;
                    String question = global_question.get(global_question_count);
                    String option1 = global_option_1.get(global_question_count);
                    String option2 = global_option_2.get(global_question_count);
                    String option3 = global_option_3.get(global_question_count);
                    String option4 = global_option_4.get(global_question_count);
                    int count = global_question_count + 1;

                    question_o_lbl.setText(question);
                    option_1_jrb.setText(option1);
                    option_2_jrb.setText(option2);
                    option_3_jrb.setText(option3);
                    option_4_jrb.setText(option4);
                    question_count_lbl.setText(String.valueOf(count));
                }
            }
        }
    }

    // Supposed to be for going back to previous question, but is not activated
    private void previous_questionButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {

        }
    }

    // Check which answer option selected
    private void optionSelectedButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
            boolean option1 = option_1_jrb.isSelected();
            boolean option2 = option_2_jrb.isSelected();
            boolean option3 = option_3_jrb.isSelected();
            boolean option4 = option_4_jrb.isSelected();

            if (option1) {
                selected_answer = "Option 1";
            }
            if (option2) {
                selected_answer = "Option 2";
            }
            if (option3) {
                selected_answer = "Option 3";
            }
            if (option4) {
                selected_answer = "Option 4";
            }
        }
    }

    // Enabling fields to edit profile
    private void editProfileButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {
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

    // Update a value In Database
    private String updateValue(String fname, String lname, String password, String email) {
        String response = "";
        synchronized (this) {
            try {
                String str = "UPDATE users SET firstName = ?, lastName = ?, password = ?, added_date = ? WHERE email = ? ";

                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) {
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
            } catch (SQLException ex) {
                System.out.println(ex);
                response = "Error Occured";
            } catch (Exception e) {
                System.out.println(e);
                response = "Error Occured";
            }
        }
        return response;
    }

    // Saving changes made to profile
    private void saveChangesButtonActionPerformed(ActionEvent evt) {
        if (connection) {
            synchronized (this) {
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
                if (dash_old_password_jpf.getText().equals("") || dash_password_jpf.getText().equals("") || dash_confirm_password_jpf.getText().equals("")) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) {

                            String response = "";
                            // Update values based on old password value
                            synchronized (this) {
                                response = updateValue(fname, lname, empty_password, email);
                            }
                            if (response.equals("Successful")) {
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
                            } else {
                                JOptionPane.showMessageDialog(MainFrame, "An Error Occured", "Error Message", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(MainFrame, ex, "Error Message", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame, e, "Error Message", JOptionPane.ERROR_MESSAGE);
                        System.out.println(e);
                    }
                } else {
                    // Check if old password input is equal to old password in DB
                    if (!(dash_old_password_jpf.getText().equals(logged_in_password))) {
                        JOptionPane.showMessageDialog(MainFrame, "Old password doesn't match", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } // Check if confirm password is equal to new password
                    else if (!(dash_confirm_password_jpf.getText().equals(dash_password_jpf.getText()))) {
                        JOptionPane.showMessageDialog(MainFrame, "Confirm password doesn't match", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp?zeroDateTimeBehavior=convertToNull", "root", "");) {

                                String response = "";
                                // Update values based on new password value
                                synchronized (this) {
                                    response = updateValue(fname, lname, password, email);
                                }
                                if (response.equals("Successful")) {
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
                                } else {
                                    JOptionPane.showMessageDialog(MainFrame, "An Error Occured", "Error Message", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(MainFrame, ex, "Error Message", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(MainFrame, e, "Error Message", JOptionPane.ERROR_MESSAGE);
                            System.out.println(e);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame, "Error: No connection to database", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Logging out users
    private void logoutButtonActionPerformed(ActionEvent evt) {
        synchronized (this) {

            logged_in_id = null;
            logged_in_fname = null;
            logged_in_lname = null;
            logged_in_email = null;
            logged_in_password = null;

            dtm_results.setRowCount(0);

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

        new QuizApp();

    }

}
