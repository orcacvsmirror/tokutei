package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * �_�C�A���O�����N���X
 * @author nishiyama
 */
public class DialogFactory {
	private static final String JKENSHINPATTERN = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrame";
	private static final String JLOGINFLAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl";
	private static final String JKEKKALISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrame";

	private static final String JKOJINFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameCtrl";
	private static final String JOUTPUTGETUJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrame";
	private static final String JOUTPUTNITIJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrame";
	private static final String JSEARCHRESULTLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrame";
	private static final String JHOKENJYAMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceListFrame";
	private static final String JTEIKEIMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken.JShokenMasterMaintenanceListFrame";
	private static final String JSHIHARAIMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListFrame";
	private static final String JKENSHINPATTERNMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrame";
	private static final String JKENSHINMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListFrame";

	// ����̂܂�
	private static final String JSHOWRESULTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JShowResultFrameCtrl";
	private static final String JKENSHINPATTERNLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameCtrl";
//	private static final String JKENSHINOUTPUTGETUJI= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameCtrl";
	// add s.inoue 2012/07/05
	private static final String JHOKENJYAMENTENANCEEDIT = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceEditFrameCtrl";
	private static final String JIMPORTDATAFROMECTRL = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameCtrl";
	private static final String JPATTERNMENTENANCEFROMECTRL = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinPatternMaintenanceEditFrameCtrl";
	// add s.inoue 2012/07/04
	private static final String JSELECTGRAPH = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrame";

	private static DialogFactory instance;
	private Map<Object, IDialog> dialogEventSrcMap;

	private static String settingVal = "���ݒ�(S)";
	private static String infomationVal = "�X�V���(I)";


	/**
	 * �R���X�g���N�^
	 */
	private DialogFactory() {
		dialogEventSrcMap = new HashMap<Object, IDialog>();
		registDialogToMap();
	}

	/**
	 * �_�C�A���O����
	 * @param parent �_�C�A���O�\�����e�E�C���h�E
	 * @param eventSrc �C�x���g�\�[�X
	 * @return
	 */
	public IDialog createDialog(JFrame parent, Object eventSrc, ArrayList<Hashtable<String, String>> selectedRow)
		throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JOUTPUTGETUJIFRAME) ||
				parentFlame.equals(JOUTPUTNITIJIFRAME)){
			return new MessageDialog(parent);
		}else if (parentFlame.equals(JKEKKALISTFRAME) ||
				parentFlame.equals(JKOJINFRAME)){
			return new DateSelectDialog(parent,selectedRow);
		}else if (parentFlame.equals(JLOGINFLAME)){

			// edit s.inoue 2010/08/02
			ActionEvent e = (ActionEvent)eventSrc;
			if (e.getActionCommand().equals(settingVal)){
				return new SettingDialog(parent);
			}else if (e.getActionCommand().equals(infomationVal)){
				return new DngPreviewHtml(parent);
			}else{
				return null;
			}
		// add s.inoue 2012/07/04
		}else if (parentFlame.equals(JSELECTGRAPH)){
            SelectSelectableGraphDialog selectselectablegraphdialog = new SelectSelectableGraphDialog(selectedRow);
            selectselectablegraphdialog.setVisible(true);
            return (IDialog)selectselectablegraphdialog;
		}else{
			return null;
		}
	}

	/**
	 * �_�C�A���O����
	 * @param parent �_�C�A���O�\�����e�E�C���h�E
	 * @param eventSrc �C�x���g�\�[�X
	 * @return
	 */
	public IDialog createDialog(JFrame parent, Object eventSrc, int selectedRow, int newPattern)
		throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JKENSHINPATTERN)){
			return new AddKenshinPatternDialog(parent,selectedRow,newPattern);
		}else{
			return null;
		}
	}
	/**
	 * �_�C�A���O����
	 * @param parent �_�C�A���O�\�����e�E�C���h�E
	 * @param eventSrc �C�x���g�\�[�X
	 * @return
	 */
	public IDialog createDialog(JFrame parent, String defaultPath)
	throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// edit s.inoue 2009/10/06
		String parentFlame = parent.getClass().getName();

		// edit s.inoue 2010/02/26
		if (parentFlame.equals(JHOKENJYAMASTERMAINTENANCE) ||
			parentFlame.equals(JSHIHARAIMASTERMAINTENANCE) ||
			parentFlame.equals(JKENSHINPATTERNMASTERMAINTENANCE) ||
			parentFlame.equals(JTEIKEIMAINTENANCE) ||
			parentFlame.equals(JKENSHINMAINTENANCE)
			// del s.inoue 2011/04/07
//			parentFlame.equals(JKENSHINOUTPUTGETUJI)
			){
			return new FileSelectDialog(parent,defaultPath);
		// add s.inoue 2010/10/20 �Ăяo�����Ή�������\
		}else if(parentFlame.equals(JHOKENJYAMENTENANCEEDIT)){
	        return new SelectOrcaHokenjyaDialog(parent, defaultPath, "�O����ORCA�ی��ҏ��Q��");
		}else if(parentFlame.equals(JIMPORTDATAFROMECTRL)){
	        return new SelectKenshinPatternDialog(parent,defaultPath, "�捞�f�[�^�ɓK�p���錒�f�p�^�[����I�����ĉ������B");
		}else if(parentFlame.equals(JPATTERNMENTENANCEFROMECTRL)){
	        return new SelectKenshinPatternDialog(parent,defaultPath, "�������錒�f�p�^�[����I�����ĉ������B");
		}else{
			return null;
		}
	}

	/*
	 * default�\��
	 */
	public IDialog createDialog(JFrame parent){
		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JSEARCHRESULTLISTFRAME) ||
				parentFlame.equals(JSHOWRESULTFRAME)){
			return new SelectPageDialog(parent);
		}else{
			return null;
		}
	}

	/**
	 * �C���X�^���X����
	 * @return DialogFactory�C���X�^���X
	 */
	public static DialogFactory getInstance() {
		if (instance == null) {
			instance = new DialogFactory();
		}
		return instance;
	}

	/**
	 * �_�C�A���O�N���X���}�b�v�ɓo�^����
	 * �����I�ɂ̓_�C�A���O�e�E�C���h�E�A�_�C�A���O�\���g���K�[�C�x���g�A
	 * �C�x���g�\�[�X�̒�`�t�@�C����ǂݍ��݁A�o�^����B
	 */
	private void registDialogToMap() {
	}
}
