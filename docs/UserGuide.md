_---_
layout: page
title: User Guide
---

MediMate (MM) is a **cross-platform desktop application** for medical professionals, specifically doctors or their receptionists,
who are experienced with computers and currently using paper records to store patient information. 
With this solution, they will be able to better manage their patient data, including updating, 
accessing and adding new patient details easily. It is optimized for use via a **Command Line Interface (CLI)** 
while still having the benefits of a Graphical User Interface (GUI). 
If you can type fast, MM can get your contact management tasks done faster than traditional GUI apps.

## Table of Contents
- [Quick start](#quick-start)
- [Features](#features)
    - [`help`](#viewing-help--help)
    - [`add`](#adding-a-patient--add)
    - [`addTime`](#adding-patient-scheduled-time--addTime)
    - [`list`](#listing-all-patients--list)
    - [`listTime`](#listing-patients-by-time--listTime)
    - [`list_name`](#listing-patients-by-name--list_name)
    - [`edit`](#editing-a-patient--edit)
    - [`find`](#locating-patients-by-name--find)
    - [`delete`](#deleting-a-patient--delete)
    - [`deletes`](#deleting-multiple-patients--deletes)
    - [`clear`](#clearing-all-entries--clear)
    - [`exit`](#exiting-the-program--exit)
    - [Saving of data](#saving-the-data)
    - [Editing of data](#editing-the-data-file)
    - [Archiving of data](#archiving-data-files-coming-in-v20)
- [FAQ](#faq)
- [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `MediMate.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your MediMate.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar MediMate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all patients.

   * `add n/John Doe p/98765432 e/johnd@example.com a/Jo0hn street, block 123, #01-01 ag/20 t/classmate m/cough` : Adds a patient named `John Doe` to the Patient List.

   * `delete 3` : Deletes the 3rd patient shown in the current list.

   * `clear` : Deletes all patients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a patient : `add`

Adds a patient to the address book:
* Enter the patient's name, phone number, email, and address.
* You can add any number of tags to the patient's profile by adding "t/" followed by the tag.
* You can also add patient's age, medical condition, time and NRIC for first meeting.
* To record a patient's other information, add them as additional tags to the patient's profile.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS ag/AGE [t/TAG]…​ m/MEDICAL_CONDITION nric/S9935010Y`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A patient can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 ag/12 t/criminal m/cough`

The first example contains strictly required information to identify a patient.

The second example contains more information relevant to that patient.

### Adding patient scheduled time : `addTime`
_Details coming in v1.3 soon ..._

### Listing all patients : `list`

Shows a list of all patients in the address book.

Format: `list`

### Listing patients by name : `list_name`

Shows a list of all patients in the address book by their name lexicographically.

Format: `list_name`

### Listing patients by time : `listTime`

_Details coming in v1.2 soon ..._

### Editing a patient : `edit`

Edits an existing patient in the patient list:

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** 1, 2, 3, …​
* Specify the field you want to edit (name, phone, email, address, or tag) followed by the new value. At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patient will be removed i.e adding of tags is not cumulative.
* You can remove all the patient’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st patient to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd patient to be `Betsy Crower` and clears all existing tags.

### Locating patients by name : `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients maching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a patient : `delete`

Deletes the specified patient from the address book.

Format: `delete INDEX`

* Deletes the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd patient in the patient list.
* `find Betsy` followed by `delete 1` deletes the 1st patient in the results of the `find` command.

### Deleting multiple patients : `deletes` 

Deletes the specified patient from the address book.

Format: `delete INDEX1 INDEX2 ...`

* Deletes the patient at the specified INDEXs.
* The index refers to the index number shown in the displayed patient list.
* The index must be a positive integer 1, 2, 3, …​

Examples:
* `list` followed by `delete 2 3` deletes the 2nd person and 3rd in the address book.
* `find Betsy` followed by `delete 1 2` deletes the 1st and 2nd person in the results of the `find` command.

### Uploading an Index File for a Patient: `upload`

The upload index command allows you to upload an index file for a specific patient.
This index file can contain additional information about the patient, such as medical history, 
test results, or treatment plans [Files allowed are PDF and images]

Format: `upload INDEX`
* Uploads an index file for the patient at the specified INDEX. 
* The index refers to the index number shown in the displayed patient list. 
* The index must be a positive integer 1, 2, 3, …​
* File must be type PDF or Image
* 
Examples: 
* To upload an index file for the patient at index 2, type:upload 2

### Clearing all entries : `clear`

Clears all entries from the patient list.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

MediMate data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

MediMate data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS ag/AGE [t/TAG]…​ m/MEDICAL_CONDITION` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**ListByName** | `list_name`
**Help** | `help`
