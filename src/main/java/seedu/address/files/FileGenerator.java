package seedu.address.files;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import seedu.address.model.person.Person;



/**
 * The type File generator.
 */
public class FileGenerator {

    //store this field at Json
    private static int formId = 0;
    private Person person;
    private String doctorName;
    private String description;
    private int days;

    /**
     * Instantiates a new File generator.
     *
     * @param person      the person
     * @param doctorName  the doctor name
     * @param description the description
     * @param days        the days
     */
    public FileGenerator(Person person, String doctorName, String description, int days) {
        this.person = person;
        this.doctorName = doctorName;
        this.description = description;
        this.days = days;
        formId++;
    }

    public static int getFormId() {
        return formId;
    }

    /**
     * Create Mc form.
     */
    public void createMcForm(String filename) {
        try {
            // Load the original PDF form
            PDDocument pdfDocument = PDDocument.load(new File("lib/MC.pdf"));

            // Get the PDF form fields
            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            //Get all fields

            List<PDField> fieldList = acroForm.getFields();

            for (PDField field: fieldList) {
                if (field instanceof PDTextField) {
                    String fileName = field.getFullyQualifiedName();
                    LocalDate now = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    switch (fileName) {
                    case "name":
                        field.setValue(person.getName().fullName);
                        break;
                    case "DOB":
                        field.setValue("222-2222");
                        break;
                    case "days":
                        field.setValue(Integer.toString(days));
                        break;
                    case "today":
                    case "startDate":
                        field.setValue(now.format(formatter));
                        break;
                    case "endDate":
                        LocalDate endDate = now.plusDays(days);
                        field.setValue(endDate.format(formatter));
                        break;
                    case "Doctor Name":
                        field.setValue(doctorName);
                        break;
                    default:
                        field.setValue(Integer.toString(formId));
                        break;
                    }
                }
            }
            //making file name unique using store MC number do file check before save.
            pdfDocument.save(new File("reports/" + person.getName().fullName + "/" + filename + "-mc.pdf"));
            pdfDocument.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Encrypt pdf.
     *
     * @param pdfDocument   the pdf document
     * @param ownerPassword the owner password
     * @param userPassword  the user password
     */
    public void encryptPdf(PDDocument pdfDocument, String ownerPassword, String userPassword) {
        try {
            int keyLength = 128; // Key length in bits (can be 40, 128, or 256 for PDFBox 2.x)

            AccessPermission accessPermission = new AccessPermission();
            StandardProtectionPolicy protectionPolicy = new StandardProtectionPolicy(ownerPassword,
                    userPassword, accessPermission);
            protectionPolicy.setEncryptionKeyLength(keyLength);
            protectionPolicy.setPermissions(accessPermission);

            pdfDocument.protect(protectionPolicy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
