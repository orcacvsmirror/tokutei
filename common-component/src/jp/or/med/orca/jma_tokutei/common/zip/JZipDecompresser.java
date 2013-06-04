package jp.or.med.orca.jma_tokutei.common.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Zip �t�@�C�����𓀂���T���v���ł��B
 * �����{��t�@�C�����A�f�B���N�g�����ɖ��Ή�
 */
public class JZipDecompresser {

//    public static void main(String[] args) {
//        JZipDecompresser za = new JZipDecompresser();
//        try {
//            File file = new File("D:/conf.zip");
//            za.unzip(file);
//        } catch (ZipException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Zip �t�@�C���̉𓀃��\�b�h
     * @param file �Ώۂ�ZIP�t�@�C��
     * @throws ZipException
     * @throws IOException
     */
    public String unzip(File file) throws ZipException, IOException {

        // �Ώۂ�Zip�t�@�C���Ɠ����̃f�B���N�g�����쐬����B
        String fileName = file.getName();
        int exindex = fileName.lastIndexOf(".");
        String baseDirName = fileName.substring(0, exindex);

        // ���W�b�N�C��
        // File baseDir = new File(file.getParent(), baseDirName);
        // if (!baseDir.mkdir()) {
        // throw new FileNotFoundException(baseDir + "�̐����Ɏ��s���܂����B");
        // }
    	int ii = 0;
        String retDirName = "";

        retDirName =baseDirName +"_" + ii;
    	File baseDir = new File(file.getParent(),retDirName);

    	while (!baseDir.mkdir()){
    		ii++;
    		retDirName = baseDirName +"_" + ii;
    		baseDir = new File(file.getParent(), retDirName);
    	}

        // Zip �t�@�C������ ZipEntry ��������o���A�t�@�C���ɕۑ����Ă����B
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();

            // �o�͐�t�@�C��
            File outFile = new File(baseDir, ze.getName());
            if (ze.isDirectory()) {
                // ZipEntry ���f�B���N�g���̏ꍇ�̓f�B���N�g�����쐬�B
                outFile.mkdirs();
            } else {

                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {
                    // ZipFile ���� �Ώ�ZipEntry �� InputStream �����o���B
                    InputStream is = zipFile.getInputStream(ze);
                    // �����悭�ǂݍ��ނ��� BufferedInputStream �Ń��b�v����B
                    bis = new BufferedInputStream(is);

                    if (!outFile.getParentFile().exists()) {
                        // �o�͐�t�@�C���̕ۑ���f�B���N�g�������݂��Ȃ��ꍇ�́A
                        // �f�B���N�g�����쐬���Ă����B
                        outFile.getParentFile().mkdirs();
                    }

                    // �o�͐� OutputStream ���쐬�B
                    bos =
                            new BufferedOutputStream(new FileOutputStream(
                                    outFile));

                    // ���̓X�g���[������ǂݍ��݁A�o�̓X�g���[���֏������ށB
                    int ava;
                    while ((ava = bis.available()) > 0) {
                        byte[] bs = new byte[ava];
                        // ����
                        bis.read(bs);

                        // �o��
                        bos.write(bs);
                    }
                } catch (FileNotFoundException e) {
                    throw e;
                } catch (IOException e) {
                    throw e;
                } finally {
                    try {
                        if (bis != null)
                            // �X�g���[���͕K�� close ����B
                            bis.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (bos != null)
                            // �X�g���[���͕K�� close ����B
                            bos.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return retDirName;
    }
}