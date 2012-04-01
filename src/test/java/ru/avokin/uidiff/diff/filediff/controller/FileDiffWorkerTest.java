package ru.avokin.uidiff.diff.filediff.controller;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;
import ru.avokin.uidiff.diff.folderdiff.model.DiffStatusEnum;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class FileDiffWorkerTest {

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

    private static final String BEAN_NAME_CODE_HIGHLIGHTER_MANAGER = "codeHighlighterManager";

    private FileDiffWorker fileDiffWorker;

    public void init(String testFolder) throws URISyntaxException {
        CodeHighlighterManager codeHighlighterManager = (CodeHighlighterManager) applicationContext.getBean(BEAN_NAME_CODE_HIGHLIGHTER_MANAGER);
        URL urlFile1 = getClass().getResource(testFolder + "/file1.txt");
        File file1 = new File(urlFile1.toURI());
        URL urlFile2 = getClass().getResource(testFolder + "/file2.txt");
        File file2 = new File(urlFile2.toURI());

        fileDiffWorker = new FileDiffWorker(file1, file2, null, codeHighlighterManager);
    }

    @Test
    public void test01() throws Exception {
        init("/filediff/test01");
        FileDiffModel model = fileDiffWorker.doInBackground();

        Assert.assertEquals(1, model.getDifferenceCount());
    }

    @Test
    public void test02() throws Exception {
        init("/filediff/test02");
        FileDiffModel model = fileDiffWorker.doInBackground();

        Assert.assertEquals(3, model.getDifferenceCount());

        Assert.assertEquals(new FileDiffCodeBlock(20, 19, DiffStatusEnum.added),
                model.getFileDifference().getDifferenceList().get(0).getLeftCodeBlock());
        Assert.assertEquals(new FileDiffCodeBlock(20, 20, DiffStatusEnum.added),
                model.getFileDifference().getDifferenceList().get(0).getRightCodeBlock());

        Assert.assertEquals(new FileDiffCodeBlock(43, 44, DiffStatusEnum.changed),
                model.getFileDifference().getDifferenceList().get(1).getLeftCodeBlock());
        Assert.assertEquals(new FileDiffCodeBlock(44, 45, DiffStatusEnum.changed),
                model.getFileDifference().getDifferenceList().get(1).getRightCodeBlock());

        Assert.assertEquals(new FileDiffCodeBlock(77, 77, DiffStatusEnum.deleted),
                model.getFileDifference().getDifferenceList().get(2).getLeftCodeBlock());
        Assert.assertEquals(new FileDiffCodeBlock(78, 77, DiffStatusEnum.deleted),
                model.getFileDifference().getDifferenceList().get(2).getRightCodeBlock());
    }
}
