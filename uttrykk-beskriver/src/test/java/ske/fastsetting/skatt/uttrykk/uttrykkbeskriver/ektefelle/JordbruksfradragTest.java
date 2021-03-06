package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ektefelle;

import static org.junit.Assert.assertNotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.Skattegrunnlag;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence.ConfluenceUttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelEktefelleUttrykkResultatKonverterer;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel.ExcelUttrykkBeskriver;

public class JordbruksfradragTest {

    private TestEktefelleUttrykkContext skattyterKontekst1;
    private TestEktefelleUttrykkContext skattyterKontekst2;

    @Before
    public void init() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,
          Belop.kr(300_000));
        Skattegrunnlag sg2 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,
          Belop.kr(400_000));

        skattyterKontekst1 = TestEktefelleUttrykkContext.ny(sg1).medEktefelle(sg2);
        skattyterKontekst2 = TestEktefelleUttrykkContext.ny(sg2).medEktefelle(sg1);
    }

    @Test
    public void testJordbruksFradrag() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        System.out.println(skattyterKontekst1.eval(jordbruksfradrag));
        System.out.println(skattyterKontekst2.eval(jordbruksfradrag));
    }

    @Test
    public void testJordbruksfradragUtenEktefelle() {
        Skattegrunnlag sg1 = new Skattegrunnlag().post(Skattegrunnlag.SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK,
          Belop.kr(300_000));

        skattyterKontekst1 = TestEktefelleUttrykkContext.ny(sg1);

        System.out.println(skattyterKontekst1.eval(SkatteberegningHelper.jordbruksfradrag()));
    }

    @Test
    public void skrivWiki() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        final ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Hovedside");

        beskriver.beskriv(skattyterKontekst1.beskrivResultat(jordbruksfradrag));
        final Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = beskriver.beskriv(skattyterKontekst2
          .beskrivResultat(jordbruksfradrag));

        assertNotNull("Sider er null", sider);

        sider.forEach((tittel, side) -> {
            System.out.println("### " + tittel + " ###");
            System.out.println(side);
        });
    }

    @Test
    public void skrivConsole() {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        final ConsoleUttrykkBeskriver beskriver = new ConsoleUttrykkBeskriver();
        ExcelEktefelleUttrykkResultatKonverterer konverterer = new ExcelEktefelleUttrykkResultatKonverterer("skatt",
          "sats");

        String res = beskriver.beskriv(konverterer.konverterResultat(skattyterKontekst1.beskrivResultat
          (jordbruksfradrag), true));

        //String res = beskriver.beskriv(skattyterKontekst2.beskrivResultat(jordbruksfradrag));

        System.out.println(res);
    }

    @Test
    public void skrivExcel() throws IOException {
        BelopUttrykk jordbruksfradrag = SkatteberegningHelper.jordbruksfradrag();

        final ExcelUttrykkBeskriver beskriver = new ExcelUttrykkBeskriver();

        ExcelEktefelleUttrykkResultatKonverterer konverterer = new ExcelEktefelleUttrykkResultatKonverterer("skatt",
          "sats");

        beskriver.beskriv(konverterer.konverterResultat(skattyterKontekst1.beskrivResultat(jordbruksfradrag), true));
        final Workbook wb = beskriver.beskriv(konverterer.konverterResultat(skattyterKontekst2.beskrivResultat
          (jordbruksfradrag), false));

        FileOutputStream out = new FileOutputStream("jordbruksfradrag.xlsx");
        wb.write(out);
        out.close();
    }
}
