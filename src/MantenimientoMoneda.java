import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import entidades.Moneda;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MantenimientoMoneda extends JFrame {

	private JPanel contentPane;
	private JTextField inputNombre;
	private JTextField inputSimbolo;
	private JTextField inputTipoCambio;
	private JTable tblMonedas;
	private DefaultTableModel tabla = new DefaultTableModel();
	private Moneda monedaSeleccionada;
	private JButton btnAñadir;
	private JButton btnReiniciar;
	private JButton btnActualizar;
	private JButton btnEliminar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MantenimientoMoneda frame = new MantenimientoMoneda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MantenimientoMoneda() {
		setType(Type.UTILITY);
		setTitle("Mantenimiento moneda");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNombre = new JLabel("Nombre: ");
		JLabel lblSimbolo = new JLabel("Símbolo:");
		JLabel lblTipoCambio = new JLabel("Tipo de cambio:");

		inputNombre = new JTextField();
		inputNombre.setColumns(10);

		inputSimbolo = new JTextField();
		inputSimbolo.setColumns(10);

		añadirColumnas();
		tblMonedas = new JTable(tabla);
		tblMonedas.getColumnModel().getColumn(0).setPreferredWidth(200);
		tblMonedas.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblMonedas.getColumnModel().getColumn(2).setPreferredWidth(100);
		JScrollPane scrollPane = new JScrollPane(tblMonedas);
		llenarTabla();

		inputTipoCambio = new JTextField();
		inputTipoCambio.setColumns(10);

		contentPane.setLayout(null);
		contentPane.add(lblNombre);
		contentPane.add(lblSimbolo);
		contentPane.add(lblTipoCambio);
		contentPane.add(inputNombre);
		contentPane.add(inputSimbolo);
		contentPane.add(inputTipoCambio);
		contentPane.add(scrollPane);

		lblNombre.setBounds(15, 19, 73, 14);
		lblSimbolo.setBounds(15, 57, 73, 14);
		lblTipoCambio.setBounds(15, 95, 83, 14);
		inputNombre.setBounds(102, 16, 185, 20);
		inputSimbolo.setBounds(102, 54, 86, 20);
		inputTipoCambio.setBounds(102, 92, 86, 20);
		scrollPane.setBounds(15, 206, 409, 136);

		btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirNuevaMoneda();
			}
		});
		btnAñadir.setBounds(325, 15, 99, 23);
		contentPane.add(btnAñadir);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarMoneda();
			}
		});
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(325, 128, 99, 23);
		contentPane.add(btnEliminar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarMoneda();
			}
		});
		btnActualizar.setBounds(325, 91, 99, 23);
		contentPane.add(btnActualizar);
		btnActualizar.setEnabled(false);

		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				btnActualizar.setEnabled(false);
				btnEliminar.setEnabled(false);
				btnAñadir.setEnabled(true);

			}
		});
		btnReiniciar.setBounds(325, 53, 99, 23);
		contentPane.add(btnReiniciar);

		JLabel lblNewLabel = new JLabel("*Para modificar datos de alguna moneda seleccionela en la tabla");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setBounds(15, 188, 409, 14);
		contentPane.add(lblNewLabel);

		tblMonedas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// seleccionamos una moneda
																								// de la tabla
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Solo se dispara una vez al completar la selección
					int selectedRow = tblMonedas.getSelectedRow();
					if (selectedRow != -1) { // Verificar que se haya seleccionado una fila válida
						String nombre = (String) tabla.getValueAt(selectedRow, 0);
						monedaSeleccionada = buscarMonedaPorNombre(nombre);
						if (monedaSeleccionada != null) {
							cargarDatos(monedaSeleccionada);// se cargan los datos de la moneda seleccionada en los
															// campos correspondientes
							btnActualizar.setEnabled(true);
							btnEliminar.setEnabled(true);
							btnAñadir.setEnabled(false);
						}
					}
				}
			}
		});
	}

	protected void actualizarMoneda() {
		Boolean ok = false; // esta variable nos permitira registrar los cambios de la moneda mientras sea true
		String nombre = inputNombre.getText();
		String simbolo = inputSimbolo.getText();
		String tipoCambio = inputTipoCambio.getText();

		ok = validarCampos(nombre, simbolo, tipoCambio); //vañlidamos que los campos sean llenados correctamente

		if (ok) {
			ok = validarMonedaEditada(nombre, simbolo);//validamos  que la moneda no repita la informacion de otras monedas
		}
		if (ok)
			guardarCambios(nombre, simbolo, tipoCambio);
	}

	private void guardarCambios(String nombre, String simbolo, String tipoCambio) {
		for (Moneda moneda : MenuPrincipal.monedas) {// recorremos el arreglos de moneda
			if (moneda == monedaSeleccionada) {// indicamos la moneda a modificar
				moneda.setNombre(nombre);
				moneda.setSimbolo(simbolo);
				moneda.setTipoCambio(Double.parseDouble(tipoCambio));
				JOptionPane.showInternalMessageDialog(null, "Se guardaron los cambios exitosamente");
				limpiarCampos();
				llenarTabla();
				btnActualizar.setEnabled(false);
				btnEliminar.setEnabled(false);
				btnAñadir.setEnabled(true);

			}
		}
	}

	private Boolean validarMonedaEditada(String nombre, String simbolo) {

		for (Moneda moneda : MenuPrincipal.monedas) {

			if (moneda != monedaSeleccionada) { // con esta condicion evitamos que la moneda se compare consigo misma
				if (moneda.getNombre().equals(nombre)) {
					JOptionPane.showInternalMessageDialog(null, "El nombre de esa moneda ya existe");
					return false;
				}
				if (moneda.getSimbolo().equals(simbolo)) {
					JOptionPane.showInternalMessageDialog(null, "El simbolo de esa moneda ya existe");
					return false;
				}
			}
		}
		return true;
	}

	protected void eliminarMoneda() {
		// confirmamos que la accion eliminar se lleve a cabo
		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar esta moneda?", "Moneda eliminada",
				JOptionPane.YES_NO_OPTION);
		if (confirmacion == JOptionPane.YES_OPTION) {// si la respuesta es si se elimina la moneda del arreglo
			MenuPrincipal.monedas.remove(monedaSeleccionada);
			limpiarCampos();
			llenarTabla();
		}
	}

	protected void cargarDatos(Moneda monedaSeleccionada) {
		inputNombre.setText(monedaSeleccionada.getNombre());
		inputSimbolo.setText(monedaSeleccionada.getSimbolo());
		inputTipoCambio.setText(monedaSeleccionada.getTipoCambio() + "");
	}

	protected Moneda buscarMonedaPorNombre(String nombre) {
		for (Moneda moneda : MenuPrincipal.monedas) {
			if (moneda.getNombre().equals(nombre)) {
				return moneda;
			}
		}
		return null;
	}

	protected void limpiarCampos() {
		inputNombre.setText("");
		inputSimbolo.setText("");
		inputTipoCambio.setText("");
		tblMonedas.clearSelection();
		tabla.setRowCount(0);

	}

	public void añadirColumnas() {
		tabla.addColumn("Nombre");
		tabla.addColumn("Símbolo");
		tabla.addColumn("Tipo de cambio");
	}

	public void llenarTabla() {
		for (Moneda moneda : MenuPrincipal.monedas) {
			tabla.addRow(new Object[] { moneda.getNombre(), moneda.getSimbolo(), moneda.getTipoCambio() });
		}
	}

	public void añadirNuevaMoneda() {

		Boolean ok = false; // esta variable nos permitira registrar la nueva moneda mientras sea true
		String nombre = inputNombre.getText();
		String simbolo = "(" + inputSimbolo.getText() + ")";
		String tipoCambio = inputTipoCambio.getText();

		// validamos primero que los campos no esten vacios
		ok = validarCampos(nombre, simbolo, tipoCambio);

		// validamos que la moneda no sea una ya existente en nuestro arreglo
		if (ok)
			ok = validarNuevaMoneda(nombre, simbolo);
		// Una vez pasadas todas las validaciones correctamente se puede registrar la
		// nueva moneda
		if (ok)
			registrarMoneda(nombre, simbolo, tipoCambio);
	}

	private void registrarMoneda(String nombre, String simbolo, String tipoCambio) {
		Moneda moneda = new Moneda(nombre, simbolo, Double.parseDouble(tipoCambio));
		MenuPrincipal.monedas.add(moneda);
		JOptionPane.showInternalMessageDialog(null, "La moneda fue añadida exitosamente");
		limpiarCampos();
		llenarTabla();
	}

	private Boolean validarNuevaMoneda(String nombre, String simbolo) {
		for (Moneda moneda : MenuPrincipal.monedas) {
			if (moneda.getNombre().equals(nombre)) {
				JOptionPane.showInternalMessageDialog(null, "El nombre de esa moneda ya existe");
				return false;
			}
			if (moneda.getSimbolo().equals(simbolo)) {
				JOptionPane.showInternalMessageDialog(null, "El simbolo de esa moneda ya existe");
				return false;
			}
		}
		return true;
	}

	private Boolean validarCampos(String nombre, String simbolo, String tipoCambio) {
		if (nombre.equals("")) {
			JOptionPane.showInternalMessageDialog(null,
					"El nombre está vacío!!!\nSe requiere de un nombre para el registro\n");
			return false;
		}
		if (simbolo.equals("")) {
			JOptionPane.showInternalMessageDialog(null,
					"El símbolo está vacío!!!\nSe requiere de un simbolo para el registro\n");
			return false;
		}
		if (tipoCambio.equals("")) {
			JOptionPane.showInternalMessageDialog(null,
					"El Tipo de cambio está vacío!!!\nSe requiere del Tipo de cambio para el registro\n");
			return false;
		}
		if (!nombre.matches("^[a-zA-ZÁÉÍÓÚáéíóúÜüÑñ\\-\\s]+$")) {
			JOptionPane.showInternalMessageDialog(null, "El nombre solo permite letras, espacios ó guiones!!!\n");
			return false;
		}
		if (!tipoCambio.matches("^\\d+(\\.\\d+)?$")) {
			JOptionPane.showInternalMessageDialog(null, "El tipo de cambio solo permite números\n");
			return false;
		}
		return true;
	}
}