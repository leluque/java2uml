package br.com.luque.java2uml.reflection.model.data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("unused")
public abstract class MixedClass extends EmptyClass implements Interface {
    private EmptyClass field1;
    private ClassWithOnlyCommonJavaTypes field2;
    private ClassWithOnlyCommonJavaTypes[] field3;
    private ClassWithOnlyCommonJavaTypes[][] field4;
    private List<ClassWithOnlyCommonJavaTypes> field5;
    private List<ClassWithOnlyCommonJavaTypes>[] field6;
    private Set<EmptyClass> field7;
    private Set<EmptyClass>[] field8;
    private Map<Long, EmptyClass> field9;
    private Map<Long, EmptyClass>[] field10;
    private Map<EmptyClass, Long> field11;
    private Map<EmptyClass, Long>[] field12;
    private ClassWithOnlyRelationships field13;
    private float field14;
    private Double field15;
    private boolean field16;
    private String field17;
    private Object field18;
    private LocalDate field19;
    private LocalDateTime field20;
    private Instant field21;

    public MixedClass() {
    }

    public MixedClass(EmptyClass field1, ClassWithOnlyCommonJavaTypes field2, ClassWithOnlyCommonJavaTypes[] field3, ClassWithOnlyCommonJavaTypes[][] field4, List<ClassWithOnlyCommonJavaTypes> field5, List<ClassWithOnlyCommonJavaTypes>[] field6, Set<EmptyClass> field7, Set<EmptyClass>[] field8, Map<Long, EmptyClass> field9, Map<Long, EmptyClass>[] field10, Map<EmptyClass, Long> field11, Map<EmptyClass, Long>[] field12, ClassWithOnlyRelationships field13, float field14, Double field15, boolean field16, String field17, Object field18, LocalDate field19, LocalDateTime field20, Instant field21) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
        this.field10 = field10;
        this.field11 = field11;
        this.field12 = field12;
        this.field13 = field13;
        this.field14 = field14;
        this.field15 = field15;
        this.field16 = field16;
        this.field17 = field17;
        this.field18 = field18;
        this.field19 = field19;
        this.field20 = field20;
        this.field21 = field21;
    }

    public EmptyClass getField1() {
        return field1;
    }

    public void setField1(EmptyClass field1) {
        this.field1 = field1;
    }

    public ClassWithOnlyCommonJavaTypes getField2() {
        return field2;
    }

    public void setField2(ClassWithOnlyCommonJavaTypes field2) {
        this.field2 = field2;
    }

    public ClassWithOnlyCommonJavaTypes[] getField3() {
        return field3;
    }

    public void setField3(ClassWithOnlyCommonJavaTypes[] field3) {
        this.field3 = field3;
    }

    public ClassWithOnlyCommonJavaTypes[][] getField4() {
        return field4;
    }

    public void setField4(ClassWithOnlyCommonJavaTypes[][] field4) {
        this.field4 = field4;
    }

    public List<ClassWithOnlyCommonJavaTypes> getField5() {
        return field5;
    }

    public void setField5(List<ClassWithOnlyCommonJavaTypes> field5) {
        this.field5 = field5;
    }

    public List<ClassWithOnlyCommonJavaTypes>[] getField6() {
        return field6;
    }

    public void setField6(List<ClassWithOnlyCommonJavaTypes>[] field6) {
        this.field6 = field6;
    }

    public Set<EmptyClass> getField7() {
        return field7;
    }

    public void setField7(Set<EmptyClass> field7) {
        this.field7 = field7;
    }

    public Set<EmptyClass>[] getField8() {
        return field8;
    }

    public void setField8(Set<EmptyClass>[] field8) {
        this.field8 = field8;
    }

    public Map<Long, EmptyClass> getField9() {
        return field9;
    }

    public void setField9(Map<Long, EmptyClass> field9) {
        this.field9 = field9;
    }

    public Map<Long, EmptyClass>[] getField10() {
        return field10;
    }

    public void setField10(Map<Long, EmptyClass>[] field10) {
        this.field10 = field10;
    }

    public Map<EmptyClass, Long> getField11() {
        return field11;
    }

    public void setField11(Map<EmptyClass, Long> field11) {
        this.field11 = field11;
    }

    public Map<EmptyClass, Long>[] getField12() {
        return field12;
    }

    public void setField12(Map<EmptyClass, Long>[] field12) {
        this.field12 = field12;
    }

    public ClassWithOnlyRelationships getField13() {
        return field13;
    }

    public void setField13(ClassWithOnlyRelationships field13) {
        this.field13 = field13;
    }

    public float getField14() {
        return field14;
    }

    public void setField14(float field14) {
        this.field14 = field14;
    }

    public Double getField15() {
        return field15;
    }

    public void setField15(Double field15) {
        this.field15 = field15;
    }

    public boolean isField16() {
        return field16;
    }

    public void setField16(boolean field16) {
        this.field16 = field16;
    }

    public String getField17() {
        return field17;
    }

    public void setField17(String field17) {
        this.field17 = field17;
    }

    public Object getField18() {
        return field18;
    }

    public void setField18(Object field18) {
        this.field18 = field18;
    }

    public LocalDate getField19() {
        return field19;
    }

    public void setField19(LocalDate field19) {
        this.field19 = field19;
    }

    public LocalDateTime getField20() {
        return field20;
    }

    public void setField20(LocalDateTime field20) {
        this.field20 = field20;
    }

    public Instant getField21() {
        return field21;
    }

    public void setField21(Instant field21) {
        this.field21 = field21;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixedClass that = (MixedClass) o;
        return Float.compare(that.field14, field14) == 0 && field16 == that.field16 && Objects.equals(field1, that.field1) && Objects.equals(field2, that.field2) && Arrays.deepEquals(field3, that.field3) && Arrays.deepEquals(field4, that.field4) && Objects.equals(field5, that.field5) && Arrays.deepEquals(field6, that.field6) && Objects.equals(field7, that.field7) && Arrays.deepEquals(field8, that.field8) && Objects.equals(field9, that.field9) && Arrays.deepEquals(field10, that.field10) && Objects.equals(field11, that.field11) && Arrays.deepEquals(field12, that.field12) && Objects.equals(field13, that.field13) && Objects.equals(field15, that.field15) && Objects.equals(field17, that.field17) && Objects.equals(field18, that.field18) && Objects.equals(field19, that.field19) && Objects.equals(field20, that.field20) && Objects.equals(field21, that.field21);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(field1, field2, field5, field7, field9, field11, field13, field14, field15, field16, field17, field18, field19, field20, field21);
        result = 31 * result + Arrays.deepHashCode(field3);
        result = 31 * result + Arrays.deepHashCode(field4);
        result = 31 * result + Arrays.deepHashCode(field6);
        result = 31 * result + Arrays.deepHashCode(field8);
        result = 31 * result + Arrays.deepHashCode(field10);
        result = 31 * result + Arrays.deepHashCode(field12);
        return result;
    }

    @Override
    public String toString() {
        return "MixedClass{" +
            "field1=" + field1 +
            ", field2=" + field2 +
            ", field3=" + Arrays.toString(field3) +
            ", field4=" + Arrays.toString(field4) +
            ", field5=" + field5 +
            ", field6=" + Arrays.toString(field6) +
            ", field7=" + field7 +
            ", field8=" + Arrays.toString(field8) +
            ", field9=" + field9 +
            ", field10=" + Arrays.toString(field10) +
            ", field11=" + field11 +
            ", field12=" + Arrays.toString(field12) +
            ", field13=" + field13 +
            ", field14=" + field14 +
            ", field15=" + field15 +
            ", field16=" + field16 +
            ", field17='" + field17 + '\'' +
            ", field18=" + field18 +
            ", field19=" + field19 +
            ", field20=" + field20 +
            ", field21=" + field21 +
            '}';
    }
}
