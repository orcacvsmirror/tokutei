// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JConstantString
{

    public JConstantString()
    {
    }

    public static final HashMap getGraphTokuteiKensa()
    {
        graphTokuteikensa = new HashMap();
        graphTokuteikensa.put(codeGraphTokuteiKey[0], codesGraphShintaisokutei);
        graphTokuteikensa.put(codeGraphTokuteiKey[1], codesGraphKekyuSantei);
        graphTokuteikensa.put(codeGraphTokuteiKey[2], codesGraphTaishaNaibunpitu);
        graphTokuteikensa.put(codeGraphTokuteiKey[3], codesGraphTaishaJunkankikei);
        graphTokuteikensa.put(codeGraphTokuteiKey[4], codesGraphNyosan);
        graphTokuteikensa.put(codeGraphTokuteiKey[5], codesGraphKetuatu);
        graphTokuteikensa.put(codeGraphTokuteiKey[6], codesGraphNyokensa);
        graphTokuteikensa.put(codeGraphTokuteiKey[7], codesGraphJinkinou);
        graphTokuteikensa.put(codeGraphTokuteiKey[8], codesGraphKankinou);
        return graphTokuteikensa;
    }

    public static final HashMap getKensahouhou()
    {
        kensahouhouCode = new HashMap();
        // eidt s.inoue 2012/07/13
        kensahouhouCode.put("9N016160100000001","����");
        kensahouhouCode.put("9N016160200000001","���ȑ���");
        kensahouhouCode.put("9N016160300000001","���Ȑ\��");
        kensahouhouCode.put("9A755000000000001","���̑�");
        kensahouhouCode.put("9A752000000000001","�Q���");
        kensahouhouCode.put("9A751000000000001","�P���");
        kensahouhouCode.put("9A765000000000001","���̑�");
        kensahouhouCode.put("9A762000000000001","�Q���");
        kensahouhouCode.put("9A761000000000001","�P���");
        kensahouhouCode.put("3F050000002327101","���z�����x�@�i�R���X�e���[���_���y�f�@)");
        kensahouhouCode.put("3F050000002327201","���O�z�����x�@�i�R���X�e���[���E���f�y�f�@)");
        kensahouhouCode.put("3F050000002399901","���̑�");
        kensahouhouCode.put("3F015000002327101","���z�����x�@�i�y�f��F�@�E�O���Z���[�������j");
        kensahouhouCode.put("3F015000002327201","���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j");
        kensahouhouCode.put("3F015000002399901","���̑�");
        kensahouhouCode.put("3F070000002327101","���z�����x�@�i���ږ@�i�񒾓a�@�j�j");
        kensahouhouCode.put("3F070000002327201","���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j");
        kensahouhouCode.put("3F070000002399901","���̑�");
        kensahouhouCode.put("3F077000002327101","���z�����x�@�i���ږ@�i�񒾓a�@�j�j");
        kensahouhouCode.put("3F077000002327201","���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j");
        kensahouhouCode.put("3F077000002399901","���̑�");
        kensahouhouCode.put("3J010000002327101","���z�����x�@�i���w�_���@�A�y�f�@�A�W�A�]�@)");
        kensahouhouCode.put("3J010000002399901","���̑�");
        kensahouhouCode.put("3B035000002327201","���O�z�����x�@�iJSCC�W�����Ή��@�j");
        kensahouhouCode.put("3B035000002399901","���̑�");
        kensahouhouCode.put("3B045000002327201","���O�z�����x�@�iJSCC�W�����Ή��@�j");
        kensahouhouCode.put("3B045000002399901","���̑�");
        kensahouhouCode.put("3B090000002327101","���z�����x�@�iJSCC�W�����Ή��@�j");
        kensahouhouCode.put("3B090000002399901","���̑�");
        kensahouhouCode.put("3B070000002327101","���z�����x�@�iJSCC�W�����Ή��@�j");
        kensahouhouCode.put("3B070000002399901","���̑�");
        kensahouhouCode.put("3C015000002327101","���z�����x�@�i�y�f�@�j");
        kensahouhouCode.put("3C015000002399901","���̑�");
        kensahouhouCode.put("3C020000002327101","���z�����x�@�i�E���J�[�[�E�y���I�L�V�^�[�[�@�j");
        kensahouhouCode.put("3C020000002399901","���̑�");
        kensahouhouCode.put("3A010000002327101","���z�����x�@�i�r�E���b�g�@�j");
        kensahouhouCode.put("3A010000002399901","���̑�");
        kensahouhouCode.put("3A015000002327101","���z�����x�@�iBCG�@�A�a�b�o���ǖ@�j");
        kensahouhouCode.put("3A015000002399901","���̑�");
        kensahouhouCode.put("5C095000002302301","�G���U�C���C���m�A�b�Z�C(EIA)");
        kensahouhouCode.put("5C095000002399901","���̑�");
        kensahouhouCode.put("3D010000001926101","�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
        kensahouhouCode.put("3D010000002227101","���z�����x�@�i�u�h�E���_���y�f�@�j");
        kensahouhouCode.put("3D010000001927201","���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
        kensahouhouCode.put("3D010000001999901","���̑�");
        kensahouhouCode.put("3D010129901926101","�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
        kensahouhouCode.put("3D010129902227101","���z�����x�@�i�u�h�E���_���y�f�@�j");
        kensahouhouCode.put("3D010129901927201","���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
        kensahouhouCode.put("3D010129901999901","���̑�");

        kensahouhouCode.put("3D045000001906202","�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j");
        kensahouhouCode.put("3D045000001920402","HPLC(�s���蕪�揜��HPLC�@�j");
        kensahouhouCode.put("3D045000001927102","�y�f�@");
        kensahouhouCode.put("3D045000001999902","���̑�");
        // add s.inoue 2013/01/21
        kensahouhouCode.put("3D046000001906202","�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j(NGSP�j");
        kensahouhouCode.put("3D046000001920402","HPLC(�s���蕪�揜��HPLC�@�j(NGSP�j");
        kensahouhouCode.put("3D046000001927102","�y�f�@(NGSP�j");
        kensahouhouCode.put("3D046000001999902","���̑�(NGSP�j");

        kensahouhouCode.put("1A020000000191111","�������@�i�@�B�ǂݎ��j");
        kensahouhouCode.put("1A020000000190111","�������@�i�ڎ��@�j");
        kensahouhouCode.put("1A010000000191111","�������@�i�@�B�ǂݎ��j");
        kensahouhouCode.put("1A010000000190111","�������@�i�ڎ��@�j");
        kensahouhouCode.put("1A100000000191111","�������@�i�@�B�ǂݎ��j");
        kensahouhouCode.put("1A100000000190111","�������@�i�ڎ��@�j");
        kensahouhouCode.put("1A030000000190301","���܌v�@");
        kensahouhouCode.put("1A030000000199901","���̑�");
        kensahouhouCode.put("2A040000001930102","���������Z�葕�u");
        kensahouhouCode.put("2A030000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A020000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A060000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A070000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A080000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A010000001930101","���������Z�葕�u");
        kensahouhouCode.put("2A050000001930101","���������Z�葕�u");
        kensahouhouCode.put("9N201000000000011","���ڎB�e");
        kensahouhouCode.put("9N206160700000011","���ڎB�e");
        kensahouhouCode.put("9N206160800000049","���ڎB�e");
        kensahouhouCode.put("9N211161100000049","���ڎB�e");
        kensahouhouCode.put("9N211161200000049","���ڎB�e");
        kensahouhouCode.put("9N216000000000011","�ԐڎB�e");
        kensahouhouCode.put("9N221160700000011","�ԐڎB�e");
        kensahouhouCode.put("9N221160800000049","�ԐڎB�e");
        kensahouhouCode.put("9N226161100000049","�ԐڎB�e");
        kensahouhouCode.put("9N226161200000049","�ԐڎB�e");
        kensahouhouCode.put("3B339000002399811","���@��킸");
        kensahouhouCode.put("1B030000001599811","���@��킸");
        kensahouhouCode.put("5D305000002399811","���@��킸");
        kensahouhouCode.put("5C070000002306201","���z�����x�@�i���e�b�N�X�ÏW����@�j");
        kensahouhouCode.put("5C070000002306301","���z�����x�@�i�Ɩ����@�j");
        kensahouhouCode.put("5C070000002399901","���̑�");
        kensahouhouCode.put("5H010000001910111","�����ǖ@�@�J�����ÏW�@");
        kensahouhouCode.put("5H010000001999911","���̑�");
        kensahouhouCode.put("5H020000001910111","�����ǖ@�@�J�����ÏW�@");
        kensahouhouCode.put("5H020000001999911","���̑�");
        kensahouhouCode.put("5E071000002399811","���@��킸");
        kensahouhouCode.put("5F016141002399811","���@��킸");
        kensahouhouCode.put("5F360149502399811","���@��킸");
        kensahouhouCode.put("5F360149702399811","���@��킸");
        kensahouhouCode.put("5F360150002399811","���@��킸");
        kensahouhouCode.put("5F360145002399811","���@��킸");
        kensahouhouCode.put("2B020000002231157","INR�l");
        kensahouhouCode.put("2B030000002231157","INR�l");
        kensahouhouCode.put("3A015000000106101","TIA");
        kensahouhouCode.put("3A025000002329201","����@");
        kensahouhouCode.put("3A030000002329201","����@");
        kensahouhouCode.put("3B010000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B010000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3B010000002399801","3:���@��킸");
        kensahouhouCode.put("3B050000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B050000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3B050000002399801","3:���@��킸");
        kensahouhouCode.put("3B110000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B110000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3B110000002399801","3:���@��킸");
        kensahouhouCode.put("3B135000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B135000002399801","2:���@��킸");
        kensahouhouCode.put("3B160000000127101","���z�����x�@");
        kensahouhouCode.put("3B160000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B160000002399801","2:���@��킸");
        kensahouhouCode.put("3B180000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B180000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3B180000002399801","3:���@��킸");
        kensahouhouCode.put("3B220000002327101","1:���z�����x�@");
        kensahouhouCode.put("3B220000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3B220000002388801","3:���@��킸");
        kensahouhouCode.put("3B340000002399801","���@��킸");
        kensahouhouCode.put("3B345000002399801","���@��킸");
        kensahouhouCode.put("3C025000002327101","1:���z�����x�@");
        kensahouhouCode.put("3C025000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3C025000002399801","3:���@��킸");
        kensahouhouCode.put("3D010100001926101","1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
        kensahouhouCode.put("3D010100001927201","3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
        kensahouhouCode.put("3D010100001999901","4:���̑��̎�@");
        kensahouhouCode.put("3D010100002227101","2�F���z�����x�@�i�u�h�E���_���y�f�@�j");
        kensahouhouCode.put("3D010106001926101","1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
        kensahouhouCode.put("3D010106001927201","3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
        kensahouhouCode.put("3D010106001999901","4:���̑��̎�@");
        kensahouhouCode.put("3D010106002227101","2�F���z�����x�@�i�u�h�E���_���y�f�@�j");
        kensahouhouCode.put("3D010112001926101","1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
        kensahouhouCode.put("3D010112001927201","3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
        kensahouhouCode.put("3D010112001999901","4:���̑�");
        kensahouhouCode.put("3D010112002227101","2�F���z�����x�@�i�u�h�E���_���y�f�@�j");
        kensahouhouCode.put("3F035000002327101","1:���z�����x�@");
        kensahouhouCode.put("3F035000002399801","2:���@���肹��");
        kensahouhouCode.put("3F065000002327101","1:���z�����x�@");
        kensahouhouCode.put("3F065000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3F065000002399801","3:���@��킸");
        kensahouhouCode.put("3F110000002327101","1:���z�����x�@");
        kensahouhouCode.put("3F110000002399801","2:���@���肹��");
        kensahouhouCode.put("3F130000002306101","1:�Ɖu����@(TIA)");
        kensahouhouCode.put("3F130000002329201","2:����@");
        kensahouhouCode.put("3F130000002399801","3:���@��킸");
        kensahouhouCode.put("3F180000002399801","�Ɖu����@");
        kensahouhouCode.put("3F185000002399801","�Ɖu����@");
        kensahouhouCode.put("3F190000002399801","�Ɖu����@");
        kensahouhouCode.put("3H010000002326101","�d�ʍ��@");
        kensahouhouCode.put("3H015000002326101","�d�ʍ��@");
        kensahouhouCode.put("3H020000002326101","�d�ʍ��@");
        kensahouhouCode.put("3H030000002327101","���z�����x�@(OCPC)");
        kensahouhouCode.put("3H040000002327101","1:���z�����x�@");
        kensahouhouCode.put("3H040000002327201","2:���O�z�����x�@(UV�@)");
        kensahouhouCode.put("3I010000002327101","���z�����x�@");
        kensahouhouCode.put("3J015000002327101","���z�����x�@");
        kensahouhouCode.put("4A025000002299801","��������E���@��킸");
        kensahouhouCode.put("4A055000002399801","��������E���@��킸");
        kensahouhouCode.put("4B010000002399801","��������E���@��킸");
        kensahouhouCode.put("4B015000002399801","��������E���@��킸");
        kensahouhouCode.put("4B035000002399801","��������E���@��킸");
        kensahouhouCode.put("5D010000002399801","��������E���@��킸");
        kensahouhouCode.put("5D015000002302311","�q�o�g�`�萫");
        kensahouhouCode.put("5D015000002399801","��������E���@��킸");
        kensahouhouCode.put("5D100000002399801","��������E���@��킸");
        kensahouhouCode.put("5D120000002399801","��������E���@��킸");
        kensahouhouCode.put("5D130000002399801","��������E���@��킸");
        kensahouhouCode.put("5E035000002306101","1:�Ɖu����@(TIA)");
        kensahouhouCode.put("5E035000002315305","2:�őf���a�����i��ߔ{���j");
        kensahouhouCode.put("5E065000000102311","1:�G���U�C���C���m�A�b�Z�C(EIA)");
        kensahouhouCode.put("5E065000002302311","2:�G���U�C���C���m�A�b�Z�C(EIA)");
        kensahouhouCode.put("5G160000002311611","���e�b�N�X�ÏW�����萫");
        kensahouhouCode.put("1A020106000190111","2:�������@�i�ڎ��@�j");
        kensahouhouCode.put("1A020106000191111","1:�������@�i�@�B�ǂݎ��j");
        kensahouhouCode.put("1A020112000190111","2:�������@�i�ڎ��@�j");
        kensahouhouCode.put("1A020112000191111","1:�������@�i�@�B�ǂݎ��j");
        kensahouhouCode.put("1A035000000190101","���w���F�@");
        kensahouhouCode.put("1A040000000190111","���w���F�@");
        kensahouhouCode.put("1A055000000190111","���w���F�@");
        kensahouhouCode.put("1A060000000190111","���w���F�@");
        kensahouhouCode.put("1A065000000190111","���w���F�@");
//        kensahouhouCode.put("9N016160100000001", "����");
//        kensahouhouCode.put("9N016160200000001", "���ȑ���");
//        kensahouhouCode.put("9N016160300000001", "���Ȑ\��");
//        kensahouhouCode.put("3F050000002327101", "���z�����x�@�i�R���X�e���[���_���y�f�@�j");
//        kensahouhouCode.put("3F050000002327201", "���O�z�����x�@�i�R���X�e���[���E���f�y�f�@�j");
//        kensahouhouCode.put("3F050000002399901", "���̑�");
//        kensahouhouCode.put("3F015000002327101", "���z�����x�@�i�y�f��F�@�E�O���Z���[�������j");
//        kensahouhouCode.put("3F015000002327201", "���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j");
//        kensahouhouCode.put("3F015000002399901", "���̑�");
//        kensahouhouCode.put("3F070000002327101", "���z�����x�@�i���ږ@�i�񒾓a�@�j�j");
//        kensahouhouCode.put("3F070000002327201", "���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j");
//        kensahouhouCode.put("3F070000002399901", "���̑�");
//        kensahouhouCode.put("3F077000002327101", "���z�����x�@�i���ږ@�i�񒾓a�@�j�j");
//        kensahouhouCode.put("3F077000002327201", "���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j");
//        kensahouhouCode.put("3F077000002399901", "���̑�");
//        kensahouhouCode.put("3J010000002327101", "���z�����x�@�i���w�_���@�A�y�f�@�A�W�A�]�@�j");
//        kensahouhouCode.put("3J010000002399901", "���̑�");
//        kensahouhouCode.put("3B035000002327201", "���O�z�����x�@�iJSCC�W�����Ή��@�j");
//        kensahouhouCode.put("3B035000002399901", "���̑�");
//        kensahouhouCode.put("3B045000002327201", "���O�z�����x�@�iJSCC�W�����Ή��@�j");
//        kensahouhouCode.put("3B045000002399901", "���̑�");
//        kensahouhouCode.put("3B090000002327101", "���z�����x�@�iJSCC�W�����Ή��@�j");
//        kensahouhouCode.put("3B090000002399901", "���̑�");
//        kensahouhouCode.put("3B070000002327101", "���z�����x�@�iJSCC�W�����Ή��@�j");
//        kensahouhouCode.put("3B070000002399901", "���̑�");
//        kensahouhouCode.put("3C015000002327101", "���z�����x�@�i�y�f�@�j");
//        kensahouhouCode.put("3C015000002399901", "���̑�");
//        kensahouhouCode.put("3C020000002327101", "���z�����x�@�i�E���J�[�[�E�y���I�L�V�^�[�[�@�j");
//        kensahouhouCode.put("3C020000002399901", "���̑�");
//        kensahouhouCode.put("3A010000002327101", "���z�����x�@�i�r�E���b�g�@�j");
//        kensahouhouCode.put("3A010000002399901", "���̑�");
//        kensahouhouCode.put("3A015000002327101", "���z�����x�@�iBCG�@�A�a�b�o���ǖ@�j");
//        kensahouhouCode.put("3A015000002399901", "���̑�");
//        kensahouhouCode.put("5C095000002302301", "�G���U�C���C���m�A�b�Z�C(EIA)");
//        kensahouhouCode.put("5C095000002399901", "���̑�");
//        kensahouhouCode.put("3D010000001926101", "�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
//        kensahouhouCode.put("3D010000002227101", "���z�����x�@�i�u�h�E���_���y�f�@�j");
//        kensahouhouCode.put("3D010000001927201", "���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
//        kensahouhouCode.put("3D010000001999901", "���̑�");
//        kensahouhouCode.put("3D010129901926101", "�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
//        kensahouhouCode.put("3D010129902227101", "���z�����x�@�i�u�h�E���_���y�f�@�j");
//        kensahouhouCode.put("3D010129901927201", "���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
//        kensahouhouCode.put("3D010129901999901", "���̑�");
//        kensahouhouCode.put("3D045000001906202", "�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j");
//        kensahouhouCode.put("3D045000001920402", "HPLC�i�s���蕪�揜��HPLC�@�j");
//        kensahouhouCode.put("3D045000001927102", "�y�f�@");
//        kensahouhouCode.put("3D045000001999902", "���̑�");
//        kensahouhouCode.put("1A020000000191111", "�������@�i�@�B�ǂݎ��j");
//        kensahouhouCode.put("1A020000000190111", "�������@�i�ڎ��@�j");
//        kensahouhouCode.put("1A010000000191111", "�������@�i�@�B�ǂݎ��j");
//        kensahouhouCode.put("1A010000000190111", "�������@�i�ڎ��@�j");
//        kensahouhouCode.put("1A100000000191111", "�������@�i�@�B�ǂݎ��j");
//        kensahouhouCode.put("1A100000000190111", "�������@�i�ڎ��@�j");
//        kensahouhouCode.put("1A030000000190301", "���܌v�@");
//        kensahouhouCode.put("1A030000000199901", "���̑�");
//        kensahouhouCode.put("5C070000002306201", "���z�����x�@�i���e�b�N�X�ÏW����@�j");
//        kensahouhouCode.put("5C070000002306301", "���z�����x�@�i�Ɩ����@�j");
//        kensahouhouCode.put("5C070000002399901", "���̑�");
//        kensahouhouCode.put("5H010000001910111", "�����ǖ@�i�J�����ÏW�@�j");
//        kensahouhouCode.put("5H010000001999911", "���̑�");
//        kensahouhouCode.put("5H020000001910111", "�����ǖ@�i�J�����ÏW�@�j");
//        kensahouhouCode.put("5H020000001999911", "���̑�");
//        kensahouhouCode.put("3A010000002327101", "���z�����x�@�i�r�E���b�g�@�j");
//        kensahouhouCode.put("3A010000002399901", "���̑�");
//        kensahouhouCode.put("3B010000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B010000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3B010000002399801", "���@��킸");
//        kensahouhouCode.put("3B050000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B050000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3B050000002399801", "���@��킸");
//        kensahouhouCode.put("3B110000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B110000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3B110000002399801", "���@��킸");
//        kensahouhouCode.put("3B050000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B050000002327201", "���@��킸");
//        kensahouhouCode.put("3B160000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B160000002399801", "���@��킸");
//        kensahouhouCode.put("3B180000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B180000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3B180000002399801", "���@��킸");
//        kensahouhouCode.put("3B220000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B220000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3B220000002388801", "���@��킸");
//        kensahouhouCode.put("3C025000002327101", "���z�����x�@");
//        kensahouhouCode.put("3C025000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3C025000002399801", "���@��킸");
//        kensahouhouCode.put("3D010100001926101", "�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
//        kensahouhouCode.put("3D010100002227101", "���z�����x�@�i�u�h�E���_���y�f�@�j");
//        kensahouhouCode.put("3D010100001927201", "���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
//        kensahouhouCode.put("3D010100001999901", "���̑��̎�@");
//        kensahouhouCode.put("3D010106001926101", "�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
//        kensahouhouCode.put("3D010106002227101", "���z�����x�@�i�u�h�E���_���y�f�@�j");
//        kensahouhouCode.put("3D010106001927201", "���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
//        kensahouhouCode.put("3D010106001999901", "���̑��̎�@");
//        kensahouhouCode.put("3D010112001926101", "�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j");
//        kensahouhouCode.put("3D010112002227101", "���z�����x�@�i�u�h�E���_���y�f�@�j");
//        kensahouhouCode.put("3D010112001927201", "���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j");
//        kensahouhouCode.put("3D010112001999901", "���̑��̎�@");
//        kensahouhouCode.put("3F035000002327101", "���z�����x�@");
//        kensahouhouCode.put("3F035000002399801", "���@��킸");
//        kensahouhouCode.put("3F065000002327101", "���z�����x�@");
//        kensahouhouCode.put("3F065000002327201", "���O�z�����x�@(UV�@)");
//        kensahouhouCode.put("3F065000002399801", "���@��킸");
//        kensahouhouCode.put("3F110000002327101", "���z�����x�@");
//        kensahouhouCode.put("3F110000002399801", "���@��킸");
//        kensahouhouCode.put("3F130000002306101", "�Ɖu����@(TIA)");
//        kensahouhouCode.put("3F130000002329201", "����@");
//        kensahouhouCode.put("3F130000002399801", "���@��킸");
//        kensahouhouCode.put("3F180000002399801", "�Ɖu����@");
//        kensahouhouCode.put("3F185000002399801", "�Ɖu����@");
//        kensahouhouCode.put("3F190000002399801", "�Ɖu����@");
//        kensahouhouCode.put("3H010000002326101", "�d�ʍ��@");
//        kensahouhouCode.put("3H015000002326101", "�d�ʍ��@");
//        kensahouhouCode.put("3H020000002326101", "�d�ʍ��@");
//        kensahouhouCode.put("3H030000002327101", "���z�����x�@(OCPC)");
//        kensahouhouCode.put("3H040000002327101", "���z�����x�@");
//        kensahouhouCode.put("3H040000002327201", "���O�z�����x�@(UV�@)");
//        // add s.inoue 2012/07/13
//        kensahouhouCode.put("3B135000002327101", "���z�����x�@");
//        kensahouhouCode.put("3B135000002399801", "���@��킸");
//        kensahouhouCode.put("3I010000002327101", "���z�����x�@");
//        kensahouhouCode.put("3J015000002327101", "���z�����x�@");
//        kensahouhouCode.put("4A025000002299801","��������E���@��킸");
//        kensahouhouCode.put("4A055000002399801","��������E���@��킸");
//        kensahouhouCode.put("4B010000002399801","��������E���@��킸");
//        kensahouhouCode.put("4B015000002399801","��������E���@��킸");
//        kensahouhouCode.put("4B035000002399801","��������E���@��킸");
//        kensahouhouCode.put("5D010000002399801","��������E���@��킸");
//        kensahouhouCode.put("5D015000002302311","�q�o�g�`�萫");
//        kensahouhouCode.put("5D015000002399801","��������E���@��킸");
//        kensahouhouCode.put("5D100000002399801","��������E���@��킸");
//        kensahouhouCode.put("5D120000002399801","��������E���@��킸");
//        kensahouhouCode.put("5D130000002399801","��������E���@��킸");
//        kensahouhouCode.put("5E035000002306101","�Ɖu����@(TIA)");
//        kensahouhouCode.put("5E035000002315305","�őf���a�����i��ߔ{���j");
//        kensahouhouCode.put("5E065000000102311","�G���U�C���C���m�A�b�Z�C(EIA)");
//        kensahouhouCode.put("5E065000002302311","�G���U�C���C���m�A�b�Z�C(EIA)");
//        kensahouhouCode.put("5G160000002311611","���e�b�N�X�ÏW�����萫");
//        kensahouhouCode.put("1A020106000190111","�������@�i�ڎ��@�j");
//        kensahouhouCode.put("1A020106000191111","�������@�i�@�B�ǂݎ��j");
//        kensahouhouCode.put("1A020112000190111","�������@�i�ڎ��@�j");
//        kensahouhouCode.put("1A020112000191111","�������@�i�@�B�ǂݎ��j");
//        kensahouhouCode.put("1A035000000190101","���w���F�@");
//        kensahouhouCode.put("1A040000000190111","���w���F�@");
//        kensahouhouCode.put("1A055000000190111","���w���F�@");
//        kensahouhouCode.put("1A060000000190111","���w���F�@");
//        kensahouhouCode.put("1A065000000190111","���w���F�@");

        return kensahouhouCode;
    }

    public static final HashMap getKensaHouhouMap(String s)
    {
        Object obj = null;
        Object obj1 = null;
        ArrayList arraylist = null;
        HashMap hashmap = new HashMap();
        if(s.equals("9N016160100000001") || s.equals("9N016160200000001") || s.equals("9N016160300000001"))
        {
            String as[] = {
                "����", "1:����", "2:���ȑ���", "3:���Ȑ\��"
            };
            arraylist = new ArrayList(Arrays.asList(as));
        } else
        if(s.equals("3F050000002327101") || s.equals("3F050000002327201") || s.equals("3F050000002399901"))
        {
            String as1[] = {
                "���R���X�e���[��", "1:���z�����x�@�i�R���X�e���[���_���y�f�@)", "2:���O�z�����x�@�i�R���X�e���[���E���f�y�f�@)", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as1));
        } else
        if(s.equals("3F015000002327101") || s.equals("3F015000002327201") || s.equals("3F015000002399901"))
        {
            String as2[] = {
                "�������b�i�g���O���Z���h�j", "1:���z�����x�@�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j", "2:���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as2));
        } else
        if(s.equals("3F070000002327101") || s.equals("3F070000002327201") || s.equals("3F070000002399901"))
        {
            String as3[] = {
                "�g�c�k�R���X�e���[��", "1:���z�����x�@�i���ږ@�i�񒾓a�@�j�j", "2:���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as3));
        } else
        if(s.equals("3F077000002327101") || s.equals("3F077000002327201") || s.equals("3F077000002399901"))
        {
            String as4[] = {
                "�k�c�k�R���X�e���[��", "1:���z�����x�@�i���ږ@�i�񒾓a�@�j�j", "2:���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as4));
        } else
        if(s.equals("3J010000002327101") || s.equals("3J010000002399901"))
        {
            String as5[] = {
                "���r�����r��", "1:���z�����x�@�i���w�_���@�A�y�f�@�A�W�A�]�@)", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as5));
        } else
        if(s.equals("3B035000002327201") || s.equals("3B035000002399901"))
        {
            String as6[] = {
                "GOT�i�`�r�s�j", "1:���O�z�����x�@�iJSCC�W�����Ή��@�j", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as6));
        } else
        if(s.equals("3B045000002327201") || s.equals("3B045000002399901"))
        {
            String as7[] = {
                "GPT�i�`�k�s�j", "1:���O�z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as7));
        } else
        if(s.equals("3B090000002327101") || s.equals("3B090000002399901"))
        {
            String as8[] = {
                "��-GT(��-GTP)", "1:���z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as8));
        } else
        if(s.equals("3B070000002327101") || s.equals("3B070000002399901"))
        {
            String as9[] = {
                "�`�k�o", "1:���z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as9));
        } else
        if(s.equals("3B070000002327101") || s.equals("3B070000002399901"))
        {
            String as10[] = {
                "�����N���A�`�j��", "1:���z�����x�@�i�y�f�@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as10));
        } else
        if(s.equals("3C020000002327101") || s.equals("3C020000002399901"))
        {
            String as11[] = {
                "�����A�_", "1:���z�����x�@�i�E���J�[�[�E�y���I�L�V�^�[�[�@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as11));
        } else
        if(s.equals("3A010000002327101") || s.equals("3A010000002399901"))
        {
            String as12[] = {
                "���`��", "1:���z�����x�@�i�r�E���b�g�@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as12));
        } else
        if(s.equals("3A015000002327101") || s.equals("3A015000002399901"))
        {
            String as13[] = {
                "�A���u�~��", "1:���z�����x�@�iBCG�@�A�a�b�o���ǖ@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as13));
        } else
        if(s.equals("5C095000002302301") || s.equals("5C095000002399901"))
        {
            String as14[] = {
                "�����t�F���`��", "1:�G���U�C���C���m�A�b�Z�C(EIA)", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as14));
        } else
        if(s.equals("3D010000001926101") || s.equals("3D010000002227101") || s.equals("3D010000001927201") || s.equals("3D010000001999901"))
        {
            String as15[] = {
                "�󕠎�����", "1:�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2:���z�����x�@�i�u�h�E���_���y�f�@�j", "3:���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as15));
        } else
        if(s.equals("3D010129901926101") || s.equals("3D010129902227101") || s.equals("3D010129901927201") || s.equals("3D010129901999901"))
        {
            String as16[] = {
                "��������", "1:�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2:���z�����x�@�i�u�h�E���_���y�f�@�j", "3:���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as16));
        } else
        if(s.equals("3D045000001906202") || s.equals("3D045000001920402") || s.equals("3D045000001927102") || s.equals("3D045000001999902"))
        {
            String as17[] = {
                "HBA1C", "1:�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j", "2�FHPLC(�s���蕪�揜��HPLC�@�j", "3�F�y�f�@", "4:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as17));
        } else
        // add s.inoue 2013/01/21
        if(s.equals("3D046000001906202") || s.equals("3D046000001920402") || s.equals("3D046000001927102") || s.equals("3D046000001999902"))
        {
            String as17_1[] = {
                "HBA1C", "1:�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j�iNGSP�j", "2�FHPLC(�s���蕪�揜��HPLC�@�j�iNGSP�j", "3�F�y�f�@�iNGSP�j", "4:���̑��iNGSP�j"
            };
            arraylist = new ArrayList(Arrays.asList(as17_1));
        } else
        if(s.equals("1A020000000191111") || s.equals("1A020000000190111"))
        {
            String as18[] = {
                "�A��", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
            };
            arraylist = new ArrayList(Arrays.asList(as18));
        } else
        if(s.equals("1A020000000191111") || s.equals("1A020000000190111"))
        {
            String as19[] = {
                "�A�`��", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
            };
            arraylist = new ArrayList(Arrays.asList(as19));
        } else
        if(s.equals("1A100000000191111") || s.equals("1A100000000190111"))
        {
            String as20[] = {
                "�A����", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
            };
            arraylist = new ArrayList(Arrays.asList(as20));
        } else
        if(s.equals("1A100000000191111") || s.equals("1A100000000190111"))
        {
            String as21[] = {
                "��d", "1:���܌v�@", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as21));
        } else
        if(s.equals("5C070000002306201") || s.equals("5C070000002306301") || s.equals("5C070000002399901"))
        {
            String as22[] = {
                "�b�q�o", "1:���z�����x�@�i���e�b�N�X�ÏW����@�j", "2:���z�����x�@�i�Ɩ����@�j", "3:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as22));
        } else
        if(s.equals("5H010000001910111") || s.equals("5H010000001999911"))
        {
            String as23[] = {
                "���t�^�i�`�a�n�j", "1:�����ǖ@�@�J�����ÏW�@", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as23));
        } else
        if(s.equals("5H020000001910111") || s.equals("5H020000001999911"))
        {
            String as24[] = {
                "���t�^�i�q���j", "1:���t�^�i�q���j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as24));
        } else
        if(s.equals("3A010000002327101") || s.equals("3A010000002399901"))
        {
            String as25[] = {
                "���`��", "1:���z�����x�@�i�r�E���b�g�@�j", "2:���̑�"
            };
            arraylist = new ArrayList(Arrays.asList(as25));
        } else
        if(s.equals("3B010000002327101") || s.equals("3B010000002327201") || s.equals("3B010000002399801"))
        {
            String as26[] = {
                "�b�j�i�b�o�j�j", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as26));
        } else
        if(s.equals("3B050000002327101") || s.equals("3B050000002327201") || s.equals("3B050000002399801"))
        {
            String as27[] = {
                "�k�c�g", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as27));
        } else
        if(s.equals("3B110000002327101") || s.equals("3B110000002327201") || s.equals("3B110000002399801"))
        {
            String as28[] = {
                "�k�c�g", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as28));
        } else
        if(s.equals("3B050000002327101") || s.equals("3B050000002327201"))
        {
            String as29[] = {
                "�k�`�o", "1:���z�����x�@", "2:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as29));
        } else
        if(s.equals("3B160000002327101") || s.equals("3B160000002399801"))
        {
            String as30[] = {
                "�����A�~���[�[", "1:���z�����x�@", "2:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as30));
        } else
        if(s.equals("3B180000002327101") || s.equals("3B180000002327201") || s.equals("3B180000002399801"))
        {
            String as31[] = {
                "�������p�[�[", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as31));
        } else
        if(s.equals("3B220000002327101") || s.equals("3B220000002327201") || s.equals("3B220000002388801"))
        {
            String as32[] = {
                "���_���t�H�X�t�@�^�[�[", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as32));
        } else
        if(s.equals("3C025000002327101") || s.equals("3C025000002327201") || s.equals("3C025000002399801"))
        {
            String as33[] = {
                "�a�t�m�i�A�f���f�j", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as33));
        } else
        if(s.equals("3D010100001926101") || s.equals("3D010100002227101") || s.equals("3D010100001927201") || s.equals("3D010100001999901"))
        {
            String as34[] = {
                "���בO�����l", "1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2�F���z�����x�@�i�u�h�E���_���y�f�@�j", "3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑��̎�@"
            };
            arraylist = new ArrayList(Arrays.asList(as34));
        } else
        if(s.equals("3D010106001926101") || s.equals("3D010106002227101") || s.equals("3D010106001927201") || s.equals("3D010106001999901"))
        {
            String as35[] = {
                "���ׂP���Ԍ㌌���l", "1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2�F���z�����x�@�i�u�h�E���_���y�f�@�j", "3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑��̎�@"
            };
            arraylist = new ArrayList(Arrays.asList(as35));
        } else
        if(s.equals("3D010112001926101") || s.equals("3D010112002227101") || s.equals("3D010112001927201") || s.equals("3D010112001999901"))
        {
            String as36[] = {
                "���ׂQ���Ԍ㌌���l", "1�F�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2�F���z�����x�@�i�u�h�E���_���y�f�@�j", "3�F���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑��̎�@"
            };
            arraylist = new ArrayList(Arrays.asList(as36));
        } else
        if(s.equals("3F035000002327101") || s.equals("3F035000002399801"))
        {
            String as37[] = {
                "�V�����b�_", "1�F���z�����x�@", "2:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as37));
        } else
        if(s.equals("3F065000002327101") || s.equals("3F065000002327201") || s.equals("3F065000002399801"))
        {
            String as38[] = {
                "�V���^�R���X�e���[��", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as38));
        } else
        if(s.equals("3F110000002327101") || s.equals("3F110000002399801"))
        {
            String as39[] = {
                "�_�`�_", "1�F���z�����x�@", "2:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as39));
        } else
        if(s.equals("3F130000002306101") || s.equals("3F130000002329201") || s.equals("3F130000002399801"))
        {
            String as40[] = {
                "�x�[�^���|�`��", "1:�Ɖu����@(TIA)", "2:����@", "3:���@��킸"
            };
            arraylist = new ArrayList(Arrays.asList(as40));
        } else
        if(s.equals("3H040000002327101") || s.equals("3H040000002327201"))
        {
            String as41[] = {
                "�������@����", "1:���z�����x�@", "2:���O�z�����x�@(UV�@)"
            };
            arraylist = new ArrayList(Arrays.asList(as41));
        }
        hashmap.put(s, arraylist);
        return hashmap;
    }

    public static final HashMap getKensaHouhouMap()
    {
        kensahouhouSet = new HashMap();
        String as[] = {
            "����", "1:����", "2:���ȑ���", "3:���Ȑ\��"
        };
        ArrayList arraylist = new ArrayList(Arrays.asList(as));
        kensahouhouSet.put("9N016160100000001", arraylist);
        String as1[] = {
            "���R���X�e���[��", "1:���z�����x�@�i�R���X�e���[���_���y�f�@)", "2:���O�z�����x�@�i�R���X�e���[���E���f�y�f�@)", "3:���̑�"
        };
        ArrayList arraylist1 = new ArrayList(Arrays.asList(as1));
        kensahouhouSet.put("3F050000002327101", arraylist1);
        String as2[] = {
            "�������b�i�g���O���Z���h�j", "1:���z�����x�@�i���z�����x�@�i�y�f��F�@�E�O���Z���[�������j", "2:���O�z�����x�@�i�y�f��F�@�E�O���Z���[�������j", "3:���̑�"
        };
        ArrayList arraylist2 = new ArrayList(Arrays.asList(as2));
        kensahouhouSet.put("3F015000002327101", arraylist2);
        String as3[] = {
            "�g�c�k�R���X�e���[��", "1:���z�����x�@�i���ږ@�i�񒾓a�@�j�j", "2:���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j", "3:���̑�"
        };
        ArrayList arraylist3 = new ArrayList(Arrays.asList(as3));
        kensahouhouSet.put("3F070000002327101", arraylist3);
        String as4[] = {
            "�k�c�k�R���X�e���[��", "1:���z�����x�@�i���ږ@�i�񒾓a�@�j�j", "2:���O�z�����x�@�i���ږ@�i�񒾓a�@�j�j", "3:���̑�"
        };
        ArrayList arraylist4 = new ArrayList(Arrays.asList(as4));
        kensahouhouSet.put("3F077000002327101", arraylist4);
        String as5[] = {
            "���r�����r��", "1:���z�����x�@�i���w�_���@�A�y�f�@�A�W�A�]�@)", "2:���̑�"
        };
        ArrayList arraylist5 = new ArrayList(Arrays.asList(as5));
        kensahouhouSet.put("3J010000002327101", arraylist5);
        String as6[] = {
            "GOT�i�`�r�s�j", "1:���O�z�����x�@�iJSCC�W�����Ή��@�j", "3:���̑�"
        };
        ArrayList arraylist6 = new ArrayList(Arrays.asList(as6));
        kensahouhouSet.put("3B035000002327201", arraylist6);
        String as7[] = {
            "GPT�i�`�k�s�j", "1:���O�z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
        };
        ArrayList arraylist7 = new ArrayList(Arrays.asList(as7));
        kensahouhouSet.put("3B045000002327201", arraylist7);
        String as8[] = {
            "��-GT(��-GTP)", "1:���z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
        };
        ArrayList arraylist8 = new ArrayList(Arrays.asList(as8));
        kensahouhouSet.put("3B090000002327101", arraylist8);
        String as9[] = {
            "�`�k�o", "1:���z�����x�@�iJSCC�W�����Ή��@�j", "2:���̑�"
        };
        ArrayList arraylist9 = new ArrayList(Arrays.asList(as9));
        kensahouhouSet.put("3B070000002327101", arraylist9);
        String as10[] = {
            "�����N���A�`�j��", "1:���z�����x�@�i�y�f�@�j", "2:���̑�"
        };
        ArrayList arraylist10 = new ArrayList(Arrays.asList(as10));
        kensahouhouSet.put("3C015000002327101", arraylist10);
        String as11[] = {
            "�����A�_", "1:���z�����x�@�i�E���J�[�[�E�y���I�L�V�^�[�[�@�j", "2:���̑�"
        };
        ArrayList arraylist11 = new ArrayList(Arrays.asList(as11));
        kensahouhouSet.put("3C020000002327101", arraylist11);
        String as12[] = {
            "���`��", "1:���z�����x�@�i�r�E���b�g�@�j", "2:���̑�"
        };
        ArrayList arraylist12 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("3A010000002327101", arraylist12);
        String as13[] = {
            "�A���u�~��", "1:���z�����x�@�iBCG�@�A�a�b�o���ǖ@�j", "2:���̑�"
        };
        ArrayList arraylist13 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("3A015000002327101", arraylist13);
        String as14[] = {
            "�����t�F���`��", "1:�G���U�C���C���m�A�b�Z�C(EIA)", "2:���̑�"
        };
        ArrayList arraylist14 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("5C095000002302301", arraylist14);
        String as15[] = {
            "�󕠎�����", "1:�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2:���z�����x�@�i�u�h�E���_���y�f�@�j", "3:���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑�"
        };
        ArrayList arraylist15 = new ArrayList(Arrays.asList(as15));
        kensahouhouSet.put("3D010000001926101", arraylist15);
        String as16[] = {
            "��������", "1:�d�ʍ��@�i�u�h�E���_���y�f�d�ɖ@�j", "2:���z�����x�@�i�u�h�E���_���y�f�@�j", "3:���O�z�����x�@�i�w�L�\�L�i�[�[�@�A�O���R�L�i�[�[�@�A�u�h�E���E���f�y�f�@�j", "4:���̑�"
        };
        ArrayList arraylist16 = new ArrayList(Arrays.asList(as16));
        kensahouhouSet.put("3D010129901926101", arraylist16);
        String as17[] = {
            "HBA1C", "1:�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j", "2�FHPLC(�s���蕪�揜��HPLC�@�j", "3�F�y�f�@", "4:���̑�"
        };
        ArrayList arraylist17 = new ArrayList(Arrays.asList(as17));
        kensahouhouSet.put("3D045000001906202", arraylist17);
        // add s.inoue 2013/01/21
        String as17_2[] = {
            "HBA1C", "1:�Ɖu�w�I���@�i���e�b�N�X�ÏW����@���j�iNGSP�j", "2�FHPLC(�s���蕪�揜��HPLC�@�j�iNGSP�j", "3�F�y�f�@�iNGSP�j", "4:���̑��iNGSP�j"
        };
        ArrayList arraylist17_2 = new ArrayList(Arrays.asList(as17_2));
        kensahouhouSet.put("3D046000001906202", arraylist17_2);

        String as18[] = {
            "�A��", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
        };
        ArrayList arraylist18 = new ArrayList(Arrays.asList(as18));
        kensahouhouSet.put("1A020000000191111", arraylist18);
        String as19[] = {
            "�A�`��", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
        };
        ArrayList arraylist19 = new ArrayList(Arrays.asList(as19));
        kensahouhouSet.put("1A010000000191111", arraylist19);
        String as20[] = {
            "�A����", "1:�������@�i�@�B�ǂݎ��j", "2:�������@�i�ڎ��@�j"
        };
        ArrayList arraylist20 = new ArrayList(Arrays.asList(as19));
        kensahouhouSet.put("1A100000000191111", arraylist20);
        String as21[] = {
            "��d", "1:���܌v�@", "2:���̑�"
        };
        ArrayList arraylist21 = new ArrayList(Arrays.asList(as21));
        kensahouhouSet.put("1A030000000190301", arraylist21);
        String as22[] = {
            "�b�q�o", "1:���z�����x�@�i���e�b�N�X�ÏW����@�j", "2:���z�����x�@�i�Ɩ����@�j", "3:���̑�"
        };
        ArrayList arraylist22 = new ArrayList(Arrays.asList(as22));
        kensahouhouSet.put("5C070000002306201", arraylist22);
        String as23[] = {
            "���t�^�i�`�a�n�j", "1:�����ǖ@�@�J�����ÏW�@", "2:���̑�"
        };
        ArrayList arraylist23 = new ArrayList(Arrays.asList(as23));
        kensahouhouSet.put("5H010000001910111", arraylist23);
        String as24[] = {
            "���t�^�i�q���j", "1:���t�^�i�q���j", "2:���̑�"
        };
        ArrayList arraylist24 = new ArrayList(Arrays.asList(as22));
        kensahouhouSet.put("5H020000001910111", arraylist22);
        String as25[] = {
            "��ꌟ���i�L�[�X���O�i�[���ށj", "��ꌟ���i�V�F�C�G���ށF�g�j", "��ꌟ���i�V�F�C�G���ށF�r�j", "��ꌟ���iSCOTT����)"
        };
        ArrayList arraylist25 = new ArrayList(Arrays.asList(as25));
        kensahouhouSet.put("5H020000001910111", arraylist25);
        return kensahouhouSet;
    }

    public static final String checkDoubleKensaHouhouMap(String as[])
    {
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        HashMap hashmap = new HashMap();
        int i = 0;
        boolean flag = false;
        boolean flag1 = false;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        int j2 = 0;
        int k2 = 0;
        int l2 = 0;
        int i3 = 0;
        int j3 = 0;
        int k3 = 0;
        int l3 = 0;
        int i4 = 0;
        int j4 = 0;
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        int j5 = 0;
        int k5 = 0;
        int l5 = 0;
        int i6 = 0;
        int j6 = 0;
        int k6 = 0;
        int l6 = 0;
        int i7 = 0;
        int j7 = 0;
        int k7 = 0;
        int l7 = 0;
        int i8 = 0;
        int j8 = 0;
        int k8 = 0;
        int l8 = 0;
        int i9 = 0;
        int j9 = 0;
        int k9 = 0;
        int l9 = 0;
        int i10 = 0;
        int j10 = 0;

        int i11 = 0;

        String s = "";
        String s1 = ",";
        for(int k10 = 0; k10 < as.length; k10++)
        {
            if(as[k10].equals("9N016160100000001") || as[k10].equals("9N016160200000001") || as[k10].equals("9N016160300000001"))
            {
                if(++i == 2)
                    s = (new StringBuilder()).append(s).append("����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F050000002327101") || as[k10].equals("3F050000002327201") || as[k10].equals("3F050000002399901"))
            {
                if(++j == 2)
                    s = (new StringBuilder()).append(s).append("���R���X�e���[��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F015000002327101") || as[k10].equals("3F015000002327201") || as[k10].equals("3F015000002399901"))
            {
                if(++k == 2)
                    s = (new StringBuilder()).append(s).append("�������b(�g���O���Z���h)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F070000002327101") || as[k10].equals("3F070000002327201") || as[k10].equals("3F070000002399901"))
            {
                if(++l == 2)
                    s = (new StringBuilder()).append(s).append("HDL�R���X�e���[��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F077000002327101") || as[k10].equals("3F077000002327201") || as[k10].equals("3F077000002399901"))
            {
                if(++i1 == 2)
                    s = (new StringBuilder()).append(s).append("LDL").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3J010000002327101") || as[k10].equals("3J010000002399901"))
            {
                if(++j1 == 2)
                    s = (new StringBuilder()).append(s).append("���r���r����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B035000002327201") || as[k10].equals("3B035000002399901"))
            {
                if(++k1 == 2)
                    s = (new StringBuilder()).append(s).append("GOT(AST)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B045000002327201") || as[k10].equals("3B045000002399901"))
            {
                if(++l1 == 2)
                    s = (new StringBuilder()).append(s).append("GPT(ALT)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B090000002327101") || as[k10].equals("3B090000002399901"))
            {
                if(++i2 == 2)
                    s = (new StringBuilder()).append(s).append("��-GT(��-GTP)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B070000002327101") || as[k10].equals("3B070000002399901"))
            {
                if(++j2 == 2)
                    s = (new StringBuilder()).append(s).append("ALP").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C015000002327101") || as[k10].equals("3C015000002399901"))
            {
                if(++k2 == 2)
                    s = (new StringBuilder()).append(s).append("�����N���`����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C020000002327101") || as[k10].equals("3C020000002399901"))
            {
                if(++l2 == 2)
                    s = (new StringBuilder()).append(s).append("�����A�_").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A010000002327101") || as[k10].equals("3A010000002399901"))
            {
                if(++i3 == 2)
                    s = (new StringBuilder()).append(s).append("���`��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A015000002327101") || as[k10].equals("3A015000002399901"))
            {
                if(++j3 == 2)
                    s = (new StringBuilder()).append(s).append("�A���u�~��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5C095000002302301") || as[k10].equals("5C095000002399901"))
            {
                if(++k3 == 2)
                    s = (new StringBuilder()).append(s).append("�����t�F���`����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010000001926101") || as[k10].equals("3D010000002227101") || as[k10].equals("3D010000001927201") || as[k10].equals("3D010000001999901"))
            {
                if(++l3 == 2)
                    s = (new StringBuilder()).append(s).append("�󕠎�����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010129901926101") || as[k10].equals("3D010129902227101") || as[k10].equals("3D010129901927201") || as[k10].equals("3D010129901999901"))
            {
                if(++i4 == 2)
                    s = (new StringBuilder()).append(s).append("��������").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D045000001906202") || as[k10].equals("3D045000001920402") || as[k10].equals("3D045000001927102") || as[k10].equals("3D045000001999902"))
            {
                if(++j4 == 2)
                    s = (new StringBuilder()).append(s).append("HBA1C").append(s1).toString();
                continue;
            }
            // add s.inoue 2013/01/21
            if(as[k10].equals("3D046000001906202") || as[k10].equals("3D046000001920402") || as[k10].equals("3D046000001927102") || as[k10].equals("3D046000001999902"))
            {
                if(++i11 == 2)
                    s = (new StringBuilder()).append(s).append("HBA1C").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A020000000191111") || as[k10].equals("1A020000000190111"))
            {
                if(++k4 == 2)
                    s = (new StringBuilder()).append(s).append("�A��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A010000000191111") || as[k10].equals("1A010000000190111"))
            {
                if(++l4 == 2)
                    s = (new StringBuilder()).append(s).append("�A�`��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A100000000191111") || as[k10].equals("1A100000000190111"))
            {
                if(++i5 == 2)
                    s = (new StringBuilder()).append(s).append("�A����").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A030000000190301") || as[k10].equals("1A030000000199901"))
            {
                if(++j5 == 2)
                    s = (new StringBuilder()).append(s).append("�A��d").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5C070000002306201") || as[k10].equals("5C070000002306301") || as[k10].equals("5C070000002399901"))
            {
                if(++k5 == 2)
                    s = (new StringBuilder()).append(s).append("CRP").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5H010000001910111") || as[k10].equals("5H010000001999911"))
            {
                if(++l5 == 2)
                    s = (new StringBuilder()).append(s).append("���t�^(ABO)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5H020000001910111") || as[k10].equals("5H020000001999911"))
            {
                if(++i6 == 2)
                    s = (new StringBuilder()).append(s).append("���t�^(RH)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A010000002327101") || as[k10].equals("3A010000002399901"))
            {
                if(++j6 == 2)
                    s = (new StringBuilder()).append(s).append("���`��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B010000002327101") || as[k10].equals("3B010000002327201") || as[k10].equals("3B010000002399801"))
            {
                if(++k6 == 2)
                    s = (new StringBuilder()).append(s).append("�b�j�i�b�o�j�j").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B050000002327101") || as[k10].equals("3B050000002327201") || as[k10].equals("3B050000002399801"))
            {
                if(++l6 == 2)
                    s = (new StringBuilder()).append(s).append("�k�c�g").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B110000002327101") || as[k10].equals("3B110000002327201") || as[k10].equals("3B110000002399801"))
            {
                if(++i7 == 2)
                    s = (new StringBuilder()).append(s).append("�R�����G�X�e���[�[").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B050000002327101") || as[k10].equals("3B050000002327201"))
            {
                if(++j7 == 2)
                    s = (new StringBuilder()).append(s).append("�k�`�o").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B160000002327101") || as[k10].equals("3B160000002399801"))
            {
                if(++k7 == 2)
                    s = (new StringBuilder()).append(s).append("�����A�~���[�[").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B180000002327101") || as[k10].equals("3B180000002327201") || as[k10].equals("3B180000002399801"))
            {
                if(++l7 == 2)
                    s = (new StringBuilder()).append(s).append("�������p�[�[").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B220000002327101") || as[k10].equals("3B220000002327201") || as[k10].equals("3B220000002388801"))
            {
                if(++i8 == 2)
                    s = (new StringBuilder()).append(s).append("���_���t�H�X�t�@�^�[�[").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C025000002327101") || as[k10].equals("3C025000002327201") || as[k10].equals("3C025000002399801"))
            {
                if(++j8 == 2)
                    s = (new StringBuilder()).append(s).append("�a�t�m�i�A�f���f�j").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010100001926101") || as[k10].equals("3D010100002227101") || as[k10].equals("3D010100001927201") || as[k10].equals("3D010100001999901"))
            {
                if(++k8 == 2)
                    s = (new StringBuilder()).append(s).append("���בO�����l").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010106001926101") || as[k10].equals("3D010106002227101") || as[k10].equals("3D010106001927201") || as[k10].equals("3D010106001999901"))
            {
                if(++l8 == 2)
                    s = (new StringBuilder()).append(s).append("���ׂP���Ԍ㌌���l").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010112001926101") || as[k10].equals("3D010112002227101") || as[k10].equals("3D010112001927201") || as[k10].equals("3D010112001999901"))
            {
                if(++i9 == 2)
                    s = (new StringBuilder()).append(s).append("���ׂQ���Ԍ㌌���l").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F035000002327101") || as[k10].equals("3F035000002399801"))
            {
                if(++j9 == 2)
                    s = (new StringBuilder()).append(s).append("�V�����b�_").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F065000002327101") || as[k10].equals("3F065000002327201") || as[k10].equals("3F065000002399801"))
            {
                if(++k9 == 2)
                    s = (new StringBuilder()).append(s).append("�V���^�R���X�e���[��").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F110000002327101") || as[k10].equals("3F110000002399801"))
            {
                if(++l9 == 2)
                    s = (new StringBuilder()).append(s).append("�_�`�_").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F130000002306101") || as[k10].equals("3F130000002329201") || as[k10].equals("3F130000002399801"))
            {
                if(++i10 == 2)
                    s = (new StringBuilder()).append(s).append("�x�[�^���|�`��").append(s1).toString();
                continue;
            }
            if((as[k10].equals("3H040000002327101") || as[k10].equals("3H040000002327201")) && ++j10 == 2)
                s = (new StringBuilder()).append(s).append("�������@����").append(s1).toString();
        }

        if(s.length() > 0)
        {
            int l10 = s.lastIndexOf(s1);
            s = s.substring(0, l10);
        }
        return s;
    }

    public static final String SEIKATU_HYOKA_1 = "9N556000000000011";
    public static final String SEIKATU_HYOKA_2 = "9N561000000000011";
    public static final String SEIKATU_HYOKA_3 = "9N566000000000049";
    public static final String ISHINO_HANDAN = "9N571000000000049";
    public static final String MONSHIN_KINOU_1 = "9N811000000000011";
    public static final String MONSHIN_KINOU_2 = "9N816000000000011";
    public static final String MONSHIN_KINOU_3 = "9N821000000000011";
    public static final String MONSHIN_KINOU_4 = "9N826000000000011";
    public static final String MONSHIN_KINOU_5 = "9N831000000000011";
    public static final String MONSHIN_KINOU_6 = "9N836000000000011";
    public static final String MONSHIN_KINOU_7 = "9N841000000000011";
    public static final String MONSHIN_KINOU_8 = "9N846000000000011";
    public static final String MONSHIN_KINOU_9 = "9N851000000000011";
    public static final String MONSHIN_KINOU_10 = "9N856000000000011";
    public static final String MONSHIN_KINOU_11 = "9N861000000000011";
    public static final String MONSHIN_KINOU_12 = "9N866000000000001";
    public static final String MONSHIN_KINOU_13 = "9N871000000000011";
    public static final String MONSHIN_KINOU_14 = "9N876000000000011";
    public static final String MONSHIN_KINOU_15 = "9N881000000000011";
    public static final String MONSHIN_KINOU_16 = "9N886000000000011";
    public static final String MONSHIN_KINOU_17 = "9N891000000000011";
    public static final String MONSHIN_KINOU_18 = "9N896000000000011";
    public static final String MONSHIN_KINOU_19 = "9N901000000000011";
    public static final String MONSHIN_KINOU_20 = "9N906000000000011";
    public static final String MONSHIN_KINOU_21 = "9N911000000000011";
    public static final String MONSHIN_KINOU_22 = "9N916000000000011";
    public static final String MONSHIN_KINOU_23 = "9N921000000000011";
    public static final String MONSHIN_KINOU_24 = "9N926000000000011";
    public static final String MONSHIN_KINOU_25 = "9N931000000000011";
    public static final String codesSeikatuKinou[] = {
        "9N811000000000011", "9N816000000000011", "9N821000000000011", "9N826000000000011", "9N831000000000011", "9N836000000000011", "9N841000000000011", "9N846000000000011", "9N851000000000011", "9N856000000000011",
        "9N861000000000011", "9N866000000000001", "9N871000000000011", "9N876000000000011", "9N881000000000011", "9N886000000000011", "9N891000000000011", "9N896000000000011", "9N901000000000011", "9N906000000000011",
        "9N911000000000011", "9N916000000000011", "9N921000000000011", "9N926000000000011", "9N931000000000011"
    };
    
    // add s.inoue 2014/07/07 �ʒm�\�̖�f�R��
	public static final String codesSeikatuKinouPrint[] = {
	"9N811000000000011","9N816000000000011","9N821000000000011","9N826000000000011",
	"9N831000000000011","9N836000000000011","9N841000000000011","9N846000000000011",
	"9N851000000000011","9N856000000000011","9N861000000000011","9N866000000000001",
	"9N871000000000011","9N876000000000011","9N881000000000011","9N886000000000011",
	"9N891000000000011","9N896000000000011","9N901000000000011","9N906000000000011",
	"9N911000000000011","9N916000000000011","9N921000000000011","9N926000000000011",
	"9N931000000000011",
	"9N556000000000011","9N561000000000011","9N566000000000049","9N571000000000049"
	};

    
    // �r�� ��f�K�{���ڔr�� "9N701000000000011", "9N706000000000011", "9N711000000000011", "9N736000000000011"
    public static final String codesSeikatuMonshin[] = {
         "9N701167000000049", "9N701167100000049", "9N706167000000049", "9N706167100000049", "9N711167000000049", "9N711167100000049", "9N716000000000011",
        "9N721000000000011", "9N726000000000011", "9N731000000000011",  "9N741000000000011", "9N746000000000011", "9N751000000000011", "9N756000000000011", "9N761000000000011", "9N766000000000011",
        "9N771000000000011", "9N776000000000011", "9N781000000000011", "9N786000000000011", "9N791000000000011", "9N796000000000011", "9N801000000000011", "9N806000000000011"
        };
// del s.inoue 2012/11/07
//    // ��f�K�{�Œǉ�
//    public static final String codesSeikatuMonshinHisu[] = {
//    	"9N701000000000011","9N706000000000011","9N711000000000011","9N736000000000011"
//    };

    public static final String codesGraphShintaisokutei[] = {
        "9N001000000000001", "9N006000000000001", "9N011000000000001", "9N016160100000001", "9N016160200000001", "9N016160300000001"
    };
    public static final String codesGraphKekyuSantei[] = {
        "2A010000001930101", "2A020000001930101", "2A030000001930101", "2A040000001930102", "2A050000001930101", "3I010000002327101"
    };
    // add s.inoue 2013/01/21
    // JDS �� NGSP
//    public static final String codesGraphTaishaNaibunpitu[] = {
//        "1A020000000191111", "1A020000000190111", "3D010000001926101", "3D010000002227101", "3D010000001927201", "3D010000001999901", "3D045000001906202", "3D045000001920402", "3D045000001927102", "3D045000001999902"
//    };
    public static final String codesGraphTaishaNaibunpitu[] = {
        "1A020000000191111", "1A020000000190111", "3D010000001926101", "3D010000002227101", "3D010000001927201", "3D010000001999901", "3D046000001906202", "3D046000001920402", "3D046000001927102", "3D046000001999902",
        // eidt s.inoue 2013/02/13
        "3D045000001906202", "3D045000001920402", "3D045000001927102", "3D045000001999902"
    };

    public static final String codesGraphTaishaJunkankikei[] = {
        "3F015000002327101", "3F015000002327201", "3F015000002399901", "3F070000002327101", "3F070000002327201", "3F070000002399901", "3F077000002327101", "3F077000002327201", "3F077000002399901"
    };
    public static final String codesGraphNyosan[] = {
        "1A035000000190101"
    };
    public static final String codesGraphKetuatu[] = {
        "9A755000000000001", "9A752000000000001", "9A751000000000001", "9A765000000000001", "9A762000000000001", "9A761000000000001"
    };
    public static final String codesGraphNyokensa[] = {
        "1A030000000190301", "1A030000000199901", "1A010000000191111", "1A010000000190111", "1A100000000191111", "1A100000000190111"
    };
    public static final String codesGraphJinkinou[] = {
        "3C025000002327101", "3C025000002327201", "3C025000002399801", "3C015000002327101", "3C015000002399901"
    };
    public static final String codesGraphKankinou[] = {
        "3A010000002327101", "3A010000002399901", "3A016000002327102", "3J010000002327101", "3J010000002399901", "3B035000002327201", "3B035000002399901", "3B045000002327201", "3B045000002399901", "3B070000002327101",
        "3B070000002399901", "3B050000002327101", "3B050000002327201", "3B050000002399801", "3B090000002327101", "3B090000002399901"
    };
    public static final String KENSAHOUHOU_FUKUI1 = "9N016160100000001";
    public static final String KENSAHOUHOU_FUKUI2 = "9N016160200000001";
    public static final String KENSAHOUHOU_FUKUI3 = "9N016160300000001";
    public static final String KENSAHOUHOU_CORESTEROLL1 = "3F050000002327101";
    public static final String KENSAHOUHOU_CORESTEROLL2 = "3F050000002327201";
    public static final String KENSAHOUHOU_CORESTEROLL3 = "3F050000002399901";
    public static final String KENSAHOUHOU_CHUSEISIBO1 = "3F015000002327101";
    public static final String KENSAHOUHOU_CHUSEISIBO2 = "3F015000002327201";
    public static final String KENSAHOUHOU_CHUSEISIBO3 = "3F015000002399901";
    public static final String KENSAHOUHOU_HDL1 = "3F070000002327101";
    public static final String KENSAHOUHOU_HDL2 = "3F070000002327201";
    public static final String KENSAHOUHOU_HDL3 = "3F070000002399901";
    public static final String KENSAHOUHOU_LDL1 = "3F077000002327101";
    public static final String KENSAHOUHOU_LDL2 = "3F077000002327201";
    public static final String KENSAHOUHOU_LDL3 = "3F077000002399901";
    public static final String KENSAHOUHOU_BIRUBIRIN1 = "3J010000002327101";
    public static final String KENSAHOUHOU_BIRUBIRIN2 = "3J010000002399901";
    public static final String KENSAHOUHOU_GOT1 = "3B035000002327201";
    public static final String KENSAHOUHOU_GOT2 = "3B035000002399901";
    public static final String KENSAHOUHOU_GPT1 = "3B045000002327201";
    public static final String KENSAHOUHOU_GPT2 = "3B045000002399901";
    public static final String KENSAHOUHOU_RGT1 = "3B090000002327101";
    public static final String KENSAHOUHOU_RGT2 = "3B090000002399901";
    public static final String KENSAHOUHOU_ALP1 = "3B070000002327101";
    public static final String KENSAHOUHOU_ALP2 = "3B070000002399901";
    public static final String KENSAHOUHOU_CREATIRIN1 = "3C015000002327101";
    public static final String KENSAHOUHOU_CREATIRIN2 = "3C015000002399901";
    public static final String KENSAHOUHOU_NYOSAN1 = "3C020000002327101";
    public static final String KENSAHOUHOU_NYOSAN2 = "3C020000002399901";
    public static final String KENSAHOUHOU_TANPAKU1 = "3A010000002327101";
    public static final String KENSAHOUHOU_TANPAKU2 = "3A010000002399901";
    public static final String KENSAHOUHOU_ARUBUMIN1 = "3A015000002327101";
    public static final String KENSAHOUHOU_ARUBUMIN2 = "3A015000002399901";
    public static final String KENSAHOUHOU_FERITIRIN1 = "5C095000002302301";
    public static final String KENSAHOUHOU_FERITIRIN2 = "5C095000002399901";
    public static final String KENSAHOUHOU_KUFUKUJI1 = "3D010000001926101";
    public static final String KENSAHOUHOU_KUFUKUJI2 = "3D010000002227101";
    public static final String KENSAHOUHOU_KUFUKUJI3 = "3D010000001927201";
    public static final String KENSAHOUHOU_KUFUKUJI4 = "3D010000001999901";
    public static final String KENSAHOUHOU_ZUIJI1 = "3D010129901926101";
    public static final String KENSAHOUHOU_ZUIJI2 = "3D010129902227101";
    public static final String KENSAHOUHOU_ZUIJI3 = "3D010129901927201";
    public static final String KENSAHOUHOU_ZUIJI4 = "3D010129901999901";
    public static final String KENSAHOUHOU_HBA1C1_JDS = "3D045000001906202";
    public static final String KENSAHOUHOU_HBA1C2_JDS = "3D045000001920402";
    public static final String KENSAHOUHOU_HBA1C3_JDS = "3D045000001927102";
    public static final String KENSAHOUHOU_HBA1C4_JDS = "3D045000001999902";
    // add s.inoue 2013/01/21
    public static final String KENSAHOUHOU_HBA1C1_NGSP = "3D046000001906202";
    public static final String KENSAHOUHOU_HBA1C2_NGSP = "3D046000001920402";
    public static final String KENSAHOUHOU_HBA1C3_NGSP = "3D046000001927102";
    public static final String KENSAHOUHOU_HBA1C4_NGSP = "3D046000001999902";

    public static final String KENSAHOUHOU_NYOTO1 = "1A020000000191111";
    public static final String KENSAHOUHOU_NYOTO2 = "1A020000000190111";
    public static final String KENSAHOUHOU_NYOTANPAKU1 = "1A010000000191111";
    public static final String KENSAHOUHOU_NYOTANPAKU2 = "1A010000000190111";
    public static final String KENSAHOUHOU_NYOSENKETU1 = "1A100000000191111";
    public static final String KENSAHOUHOU_NYOSENKETU2 = "1A100000000190111";
    public static final String KENSAHOUHOU_HIJYU1 = "1A030000000190301";
    public static final String KENSAHOUHOU_HIJYU2 = "1A030000000199901";
    public static final String KENSAHOUHOU_CRP1 = "5C070000002306201";
    public static final String KENSAHOUHOU_CRP2 = "5C070000002306301";
    public static final String KENSAHOUHOU_CRP3 = "5C070000002399901";
    public static final String KENSAHOUHOU_ABO1 = "5H010000001910111";
    public static final String KENSAHOUHOU_ABO2 = "5H010000001999911";
    public static final String KENSAHOUHOU_RH1 = "5H020000001910111";
    public static final String KENSAHOUHOU_RH2 = "5H020000001999911";
    public static final String KENSAHOUHOU_FUKA1_KIKAI = "1A020106000191111";
    public static final String KENSAHOUHOU_FUKA1_MOKUSHI = "1A020106000190111";
    public static final String KENSAHOUHOU_FUKA2_KIKAI = "1A020112000191111";
    public static final String KENSAHOUHOU_FUKA2_MOKUSHI = "1A020112000190111";
    public static final String KENSAHOUHOU_SOUTANPAKU_KASHI = "3A010000002327101";
    public static final String KENSAHOUHOU_SOUTANPAKU_SONOTA = "3A010000002399901";
    public static final String KENSAHOUHOU_CK_KASHI = "3B010000002327101";
    public static final String KENSAHOUHOU_CK_UV = "3B010000002327201";
    public static final String KENSAHOUHOU_CK_SONOTA = "3B010000002399801";
    public static final String KENSAHOUHOU_LDH_KASHI = "3B050000002327101";
    public static final String KENSAHOUHOU_LDH_UV = "3B050000002327201";
    public static final String KENSAHOUHOU_LDH_SONOTA = "3B050000002399801";
    public static final String KENSAHOUHOU_CORINTERAZE_KASHI = "3B110000002327101";
    public static final String KENSAHOUHOU_CORINTERAZE_UV = "3B110000002327201";
    public static final String KENSAHOUHOU_CORINTERAZE_SONOTA = "3B110000002399801";
    public static final String KENSAHOUHOU_LAP_KASHI = "3B050000002327101";
    public static final String KENSAHOUHOU_LAP_SONOTA = "3B050000002327201";
    public static final String KENSAHOUHOU_KESEIAMIRAZE_KASHI = "3B160000002327101";
    public static final String KENSAHOUHOU_KESEIAMIRAZE_SONOTA = "3B160000002399801";
    public static final String KENSAHOUHOU_KESEIRIPAZE_KASHI = "3B180000002327101";
    public static final String KENSAHOUHOU_KESEIRIPAZE_UV = "3B180000002327201";
    public static final String KENSAHOUHOU_KESEIRIPAZE_SONOTA = "3B180000002399801";
    public static final String KENSAHOUHOU_SOUSANSEI_KASHI = "3B220000002327101";
    public static final String KENSAHOUHOU_SOUSANSEI_UV = "3B220000002327201";
    public static final String KENSAHOUHOU_SOUSANSEI_SONOTA = "3B220000002388801";
    public static final String KENSAHOUHOU_BUN_KASHI = "3C025000002327101";
    public static final String KENSAHOUHOU_BUN_UV = "3C025000002327201";
    public static final String KENSAHOUHOU_BUN_SONOTA = "3C025000002399801";
    public static final String KENSAHOUHOU_FUKA_DENI = "3D010100001926101";
    public static final String KENSAHOUHOU_FUKA_KASHI = "3D010100002227101";
    public static final String KENSAHOUHOU_FUKA_UV = "3D010100001927201";
    public static final String KENSAHOUHOU_FUKA_SONOTA = "3D010100001999901";
    public static final String KENSAHOUHOU_FUKA1_DENI = "3D010106001926101";
    public static final String KENSAHOUHOU_FUKA1_KASHI = "3D010106002227101";
    public static final String KENSAHOUHOU_FUKA1_UV = "3D010106001927201";
    public static final String KENSAHOUHOU_FUKA1_SONOTA = "3D010106001999901";
    public static final String KENSAHOUHOU_FUKA2_DENI = "3D010112001926101";
    public static final String KENSAHOUHOU_FUKA2_KASHI = "3D010112002227101";
    public static final String KENSAHOUHOU_FUKA2_UV = "3D010112001927201";
    public static final String KENSAHOUHOU_FUKA2_SONOTA = "3D010112001999901";
    public static final String KENSAHOUHOU_YURI_KASHI = "3F035000002327101";
    public static final String KENSAHOUHOU_YURI_SONOTA = "3F035000002399801";
    public static final String KENSAHOUHOU_YURICORESTEROL_KASHI = "3F065000002327101";
    public static final String KENSAHOUHOU_YURICORESTEROL_UV = "3F065000002327201";
    public static final String KENSAHOUHOU_YURICORESTEROL_SONOTA = "3F065000002399801";
    public static final String KENSAHOUHOU_TANJYU_KASHI = "3F110000002327101";
    public static final String KENSAHOUHOU_TANJYU_SONOTA = "3F110000002399801";
    public static final String KENSAHOUHOU_BETAROPO_KASHI = "3F130000002306101";
    public static final String KENSAHOUHOU_BETAROPO_UV = "3F130000002329201";
    public static final String KENSAHOUHOU_BETAROPO_SONOTA = "3F130000002399801";
    public static final String KENSAHOUHOU_KESEIMUKIRIN_KASHI = "3H040000002327101";
    public static final String KENSAHOUHOU_KESEIMUKIRIN_SONOTA = "3H040000002327201";
    public static HashMap kensahouhouCode = null;
    public static HashMap graphTokuteikensa = null;
    public static HashMap kensahouhouSet = null;
    public static final String COMBO_METABO_CODE = "9N501000000000011";
    public static final String COMBO_HOKENSIDO_CODE = "9N506000000000011";
    public static final String COLUMN_NAME_HISU_FLG = "HISU_FLG";
    public static final String COLUMN_NAME_JYOUGEN = "JYOUGEN";
    public static final String COLUMN_NAME_KAGEN = "KAGEN";
    public static final String COLUMN_NAME_KOMENTO = "KOMENTO";
    public static final String COLUMN_NAME_HANTEI = "HANTEI";
    public static final String COLUMN_NAME_H_L = "H_L";
    public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
    public static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
    public static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
    public static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
    public static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
    public static final String COLUMN_NAME_CODE_NUM = "CODE_NUM";
    public static final String COLUMN_NAME_CODE_NAME = "CODE_NAME";
    public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
    public static final String COLUMN_NAME_MAX_BYTE_LENGTH = "MAX_BYTE_LENGTH";
    public static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
    public static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
    public static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKUNAME";
    public static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKUCD";
    public static final String COLUMN_NAME_TANI = "TANI";
    public static final String COLUMN_NAME_HIS_TI = "HIS_TI";
    public static final String CODE_MONSHIN_HEADER = "9N7";
    public static final String CODE_SEIKATU_KAIZEN = "9N801000000000011";
    public static final String CODE_HOKEN_SHIDOU = "9N806000000000011";
    public static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
    public static final String CODE_ISHINOHANDAN_1 = "9N501000000000011";
    public static final String CODE_ISHINOHANDAN_2 = "9N506000000000011";
    public static final String CODE_ISHINOHANDAN_3 = "9N511000000000049";
    public static final String CODE_ISHINOHANDAN_4 = "9N516000000000049";
    public static final String codeGraphTokuteiKey[] = {
        "�g�̑���", "�����Z��", "���(������n)", "���(�z��n)", "���(�A�_)", "����", "�A����", "�t�@�\", "�̋@�\"
    };

}
