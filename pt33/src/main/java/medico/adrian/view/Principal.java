package medico.adrian.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import medico.adrian.model.BaseX_API;
import medico.adrian.model.Incrustado;
import medico.adrian.model.Pais;
import medico.adrian.model.Pt33Manager;
import medico.adrian.model.Server_XQJ;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;


public class Principal extends JFrame {

	private final ButtonGroup buttonGroup = new ButtonGroup();
	public static JComboBox<String> myComboBox;
	public List<String> nombresPaises;
	/*
	 * Interfaz con metodos 
	 */
	public static Pt33Manager man;
	public JTextArea textArea;

	// public JRadioButtonMenuItem incrustado, csBaseX, csXQJ;
	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// public List<String> nombresPaises;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					man = new Incrustado();
					// Incrustado inc = new Incrustado();
					Principal principal = new Principal(man.getPaises());

					// man.generarInfoPais(myComboBox.getSelectedItem().toString());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Principal(List<String> nombresPaises) {
		this.nombresPaises = nombresPaises;
		String[] paises = nombresPaises.toArray(new String[0]);
		new JFrame();
		setBounds(100, 100, 577, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		this.myComboBox = new JComboBox(paises);
	/*
	 * Genero los componentes graficos
	 */
		myComboBox.setBounds(12, 13, 370, 28);
		getContentPane().add(myComboBox);
		myComboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				// AQUI DE MOMENTO NADA
			}
		});

		JButton btnGenera = new JButton("Generar");
		btnGenera.setMnemonic('G');
		btnGenera.setBounds(425, 15, 97, 25);
		getContentPane().add(btnGenera);
		btnGenera.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				// System.out.println(myComboBox.getSelectedItem().toString());
				man.generarInfoPais(myComboBox.getSelectedItem().toString());
				Pais p = man.getPais();
				System.out.println(p.getNom());
				textArea.setText("");
				textArea.append(p.getNom());

				String totalKmFroteras = String.valueOf(p.getLongitudFronteres());

				textArea.append("\nFronteras: " + totalKmFroteras + " km");
				textArea.append("\nEtnias: ");
				p.getGrupsEtnics().forEach(item -> {
					textArea.append(item.toString());
				});

			}
		});

		JButton btnHtml = new JButton("HTML");
		btnHtml.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHtml.setBounds(425, 53, 97, 25);
		getContentPane().add(btnHtml);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 98, 535, 436);
		getContentPane().add(scrollPane);
		

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		btnHtml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String result = man.generarHTML();
				textArea.setText(result);

				//create file
				File f = new File("htmlPaisos.html");
				
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(f, "UTF-8");
					pw.write(result);
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} finally {
					pw.close();
				}
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMode = new JMenu("Modo");
		menuBar.add(mnMode);
		
		JRadioButtonMenuItem incrustado = new JRadioButtonMenuItem("Incrustado");
		incrustado.setSelected(true);
		buttonGroup.add(incrustado);
		mnMode.add(incrustado);
		incrustado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
//				if (((JRadioButtonMenuItem) e.getSource()).isSelected()) {
//				}
				man = new Incrustado();
			}
		});

		JRadioButtonMenuItem csBaseX = new JRadioButtonMenuItem("Cliente - Servidor API BaseX");
		buttonGroup.add(csBaseX);
		mnMode.add(csBaseX);
		csBaseX.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(csBaseX.isSelected()) {
				System.out.println("FLAAAAAAAAg!!");
					man = new BaseX_API();
				}
			}
		});
//		csBaseX.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				super.mouseClicked(e);
//				if (((JRadioButtonMenuItem) e.getSource()).isSelected()) {
//				}
//			}
//		});

		JRadioButtonMenuItem csXQJ = new JRadioButtonMenuItem("Cliente - Servidor API XQJ");
		buttonGroup.add(csXQJ);
		mnMode.add(csXQJ);
		csXQJ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(csXQJ.isSelected()) {
					man = new BaseX_API();
				}
			}
		});
		man = new Server_XQJ();
		setVisible(true);
	}

	public static String getComboBoxItem() {
		return myComboBox.getSelectedItem().toString();
	}
}
