/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aquarium;

/**
 *
 * @author Dexp
 */
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentChangeListener implements DocumentListener {
	@Override
	public void insertUpdate(DocumentEvent e) {
		changeText();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeText();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changeText();
	}

	protected abstract void changeText();
}
