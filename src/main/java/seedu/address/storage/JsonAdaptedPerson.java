package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalCondition;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private String medicalCondition;
    private String age;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private LocalDateTime time = null;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */

    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("time") String time,
                             @JsonProperty("age") String age,
                             @JsonProperty("MedicalCondition") String medicalCondition) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        if (age != null) {
            this.age = age;
        } else {
            this.age = "";
        }

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (time != null) {
            this.time = LocalDateTime.parse(time);
        }
        if (medicalCondition != null) {
            this.medicalCondition = medicalCondition;
        }
    }

    public JsonAdaptedPerson(String name, String phone, String email, String address, List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    public JsonAdaptedPerson(String name, String phone, String email, String address,
                             String age, List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.age = age;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        if (source.hasTime()) {
            time = source.getTime();
        }
        if (source.getAge().getAge() != null) {
            age = source.getAge().getAge();
        }
        if (source.getMedicalCondition().getValue() != null) {
            medicalCondition = source.getMedicalCondition().getValue();
        }
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Age modelAge = new Age(age);
        final MedicalCondition modelMedical = new MedicalCondition(medicalCondition);

        if (age != null && medicalCondition != null) {
            if (time == null) {
                return new Person(modelName, modelPhone, modelEmail, modelAddress, modelAge, modelTags, modelMedical);
            } else {
                return new Person(modelName, modelPhone, modelEmail, modelAddress,
                    modelAge, modelTags, time, modelMedical);
            }
        }
        if (age != null && medicalCondition == null) {
            if (time == null) {
                return new Person(modelName, modelPhone, modelEmail, modelAddress, modelAge, modelTags);
            } else {
                return new Person(modelName, modelPhone, modelEmail, modelAddress, modelAge, modelTags, time);
            }
        }
        if (age == null && medicalCondition != null) {
            if (time == null) {
                return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelMedical);
            } else {
                return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, time, modelMedical);
            }
        }
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }
}
