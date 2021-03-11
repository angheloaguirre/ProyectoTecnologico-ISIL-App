package ConnectionDB;

public class Especialidad {
    private String especialidad = "";

    public Especialidad() {

    }

    public Especialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return especialidad;
    }
}
