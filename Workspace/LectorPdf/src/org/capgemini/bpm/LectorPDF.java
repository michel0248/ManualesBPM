package org.capgemini.bpm;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.fields.PdfField;
import com.spire.pdf.widget.PdfCheckBoxWidgetFieldWidget;
import com.spire.pdf.widget.PdfComboBoxWidgetFieldWidget;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfListBoxWidgetFieldWidget;
import com.spire.pdf.widget.PdfListWidgetItem;
import com.spire.pdf.widget.PdfListWidgetItemCollection;
import com.spire.pdf.widget.PdfRadioButtonListFieldWidget;
import com.spire.pdf.widget.PdfTextBoxFieldWidget;

import java.io.FileWriter;
import java.io.IOException;

public class LectorPDF {

	public static void main(String[] args) {

		// Carga El Documento PDF
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile("doc.pdf");

		// Obtiene los Campos del formulario

		PdfFormWidget formWidget = (PdfFormWidget) pdf.getForm();
		StringBuilder sb = new StringBuilder();

		// Recorre los campos del formulario y extrae el valor de cada campo

		for (int i = 0; i < formWidget.getFieldsWidget().getCount(); i++)

		{

			PdfField field = (PdfField) formWidget.getFieldsWidget().getList()
					.get(i);

			if (field instanceof PdfTextBoxFieldWidget)

			{

				PdfTextBoxFieldWidget textBoxField = (PdfTextBoxFieldWidget) field;

				// obtiene valores de textField

				String text = textBoxField.getText();

				sb.append("El texto en textbox: " + text + "\r\n");

			}

			if (field instanceof PdfListBoxWidgetFieldWidget)

			{

				PdfListBoxWidgetFieldWidget listBoxField = (PdfListBoxWidgetFieldWidget) field;

				sb.append("Elementos de la lista son: \r\n");

				// obtiene valores de Listbox

				PdfListWidgetItemCollection items = listBoxField.getValues();

				for (PdfListWidgetItem item : (Iterable<PdfListWidgetItem>) items)

				{

					sb.append(item.getValue() + "\r\n");

				}

				// obtiene valores de selected en una lista

				String selectedValue = listBoxField.getSelectedValue();

				sb.append("el valor seleccionado en la lista es: "
						+ selectedValue + "\r\n");

			}

			if (field instanceof PdfComboBoxWidgetFieldWidget)

			{

				PdfComboBoxWidgetFieldWidget comBoxField = (PdfComboBoxWidgetFieldWidget) field;

				sb.append("el valor del combobox es: \r\n");

				// obtiene los valores de un combobox

				PdfListWidgetItemCollection items = comBoxField.getValues();

				for (PdfListWidgetItem item : (Iterable<PdfListWidgetItem>) items)

				{

					sb.append(item.getValue() + "\r\n");

				}

				// obtiene los valores de un selected

				String selectedValue = comBoxField.getSelectedValue();

				sb.append("El valor Seleccionado en el combobox es: "
						+ selectedValue + "\r\n");

			}

			if (field instanceof PdfRadioButtonListFieldWidget)

			{

				PdfRadioButtonListFieldWidget radioBtnField = (PdfRadioButtonListFieldWidget) field;

				// obtiene los valores de radioButton

				String value = radioBtnField.getValue();

				sb.append("el texto en el radio es: " + value + "\r\n");

			}

			if (field instanceof PdfCheckBoxWidgetFieldWidget)

			{

				PdfCheckBoxWidgetFieldWidget checkBoxField = (PdfCheckBoxWidgetFieldWidget) field;

				// obtiene el estado de un checkbox

				boolean state = checkBoxField.getChecked();

				sb.append("esta seleccionado el checkbox? " + state + "\r\n");

			}

		}

		try {

			// Write text into a .txt file

			FileWriter writer = new FileWriter("Todos Los elementos.txt");

			writer.write(sb.toString());

			writer.flush();

		} catch (IOException e) {

			e.printStackTrace();

		}

		pdf.close();

	}
}
