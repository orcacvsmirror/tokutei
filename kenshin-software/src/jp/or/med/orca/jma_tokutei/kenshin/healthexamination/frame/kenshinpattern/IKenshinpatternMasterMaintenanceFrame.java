package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import org.openswing.swing.client.GridControl;

public interface IKenshinpatternMasterMaintenanceFrame{

	/* control���� */
	public void setControl(Connection conn,
			JKenshinpatternMasterMaintenanceListFrameCtrl controller);

	/* �����[�h */
	public void reloadData();

	/* ������ */
	void jbInit();

	/* �O���b�hgetter */
	public GridControl getGrid();

}
