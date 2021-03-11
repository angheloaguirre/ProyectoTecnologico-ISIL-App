package ConnectionDB;

public class Doctor {
    private String nombres;
    private String apePa;
    private String apeMa;
    private String codigo;
    private String exp;
    private String precio;
    private String modalidad;
    private String numCelular;
    private String direccion;
    private String correo;

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    private String especialidad;

    public Doctor(){}

    public Doctor(String nombres, String apePa, String apeMa, String codigo, String exp, String precio, String modalidad, String numCelular, String direccion, String correo, String especialidad) {
        this.nombres = nombres;
        this.apePa = apePa;
        this.apeMa = apeMa;
        this.codigo = codigo;
        this.exp = exp;
        this.precio = precio;
        this.modalidad = modalidad;
        this.numCelular = numCelular;
        this.direccion = direccion;
        this.correo = correo;
        this.especialidad = especialidad;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getModalidad() { return modalidad; }

    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApePa() {
        return apePa;
    }

    public void setApePa(String apePa) {
        this.apePa = apePa;
    }

    public String getApeMa() {
        return apeMa;
    }

    public void setApeMa(String apeMa) {
        this.apeMa = apeMa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "nombres='" + nombres + '\'' +
                ", apePa='" + apePa + '\'' +
                ", apeMa='" + apeMa + '\'' +
                ", codigo='" + codigo + '\'' +
                ", exp='" + exp + '\'' +
                ", precio='" + precio + '\'' +
                ", modalidad='"+ modalidad + '\'' +
                ", numCelular='"+ numCelular + '\'' +
                ", correo='"+ correo + '\'' +
                ", especialidad='"+ especialidad + '\'' +
                ", direccion='"+ direccion + '\'' +
                '}';
    }

    public boolean estaVacio() {
        if (nombres.equals("") || apePa.equals("") || apeMa.equals("") || codigo.equals("") || exp.equals("") || precio.equals("") || modalidad.equals("")) {
            return true;
        }
        return false;
    }
}
