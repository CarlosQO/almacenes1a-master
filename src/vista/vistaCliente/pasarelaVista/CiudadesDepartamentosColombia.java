package vista.vistaCliente.pasarelaVista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class CiudadesDepartamentosColombia {
    public static List<String> obtenerCiudadesPorDepartamento(String departamento) {
        List<String> ciudades = new ArrayList<>();
        switch (departamento) {
            case "Amazonas":
                ciudades.add("Leticia");
                ciudades.add("Puerto Nariño");
                break;

            case "Antioquia":
                ciudades.add("Medellín");
                ciudades.add("Bello");
                ciudades.add("Itagüí");
                ciudades.add("Envigado");
                ciudades.add("Rionegro");
                break;

            case "Arauca":
                ciudades.add("Arauca");
                ciudades.add("Saravena");
                ciudades.add("Tame");
                ciudades.add("Arauquita");
                break;

            case "Atlántico":
                ciudades.add("Barranquilla");
                ciudades.add("Soledad");
                ciudades.add("Malambo");
                ciudades.add("Sabanalarga");
                ciudades.add("Puerto Colombia");
                break;

            case "Bolívar":
                ciudades.add("Cartagena");
                ciudades.add("Magangué");
                ciudades.add("Turbaco");
                ciudades.add("Arjona");
                ciudades.add("El Carmen de Bolívar");
                break;

            case "Boyacá":
                ciudades.add("Tunja");
                ciudades.add("Duitama");
                ciudades.add("Sogamoso");
                ciudades.add("Chiquinquirá");
                ciudades.add("Paipa");
                break;
            case "Caldas":
                ciudades.add("Manizales");
                ciudades.add("Villamaría");
                ciudades.add("Chinchiná");
                ciudades.add("La Dorada");
                ciudades.add("Neira");
                break;

            case "Caquetá":
                ciudades.add("Florencia");
                ciudades.add("Belén de los Andaquíes");
                ciudades.add("San Vicente del Caguán");
                ciudades.add("Puerto Rico");
                ciudades.add("Cartagena del Chairá");
                break;

            case "Casanare":
                ciudades.add("Yopal");
                ciudades.add("Aguazul");
                ciudades.add("Villanueva");
                ciudades.add("Tauramena");
                ciudades.add("Monterrey");
                break;

            case "Cauca":
                ciudades.add("Popayán");
                ciudades.add("Santander de Quilichao");
                ciudades.add("Puerto Tejada");
                ciudades.add("El Tambo");
                ciudades.add("Patía");
                break;

            case "Cesar":
                ciudades.add("Valledupar");
                ciudades.add("Aguachica");
                ciudades.add("La Jagua de Ibirico");
                ciudades.add("Bosconia");
                ciudades.add("Curumaní");
                break;

            case "Chocó":
                ciudades.add("Quibdó");
                ciudades.add("Istmina");
                ciudades.add("Tadó");
                ciudades.add("Condoto");
                ciudades.add("Bahía Solano");
                break;
            case "Córdoba":
                ciudades.add("Montería");
                ciudades.add("Lorica");
                ciudades.add("Sahagún");
                ciudades.add("Cereté");
                ciudades.add("Tierralta");
                break;

            case "Cundinamarca":
                ciudades.add("Bogotá D.C.");
                ciudades.add("Soacha");
                ciudades.add("Zipaquirá");
                ciudades.add("Facatativá");
                ciudades.add("Chía");
                break;

            case "Guainía":
                ciudades.add("Inírida");
                ciudades.add("Barranco Minas");
                ciudades.add("Mapiripana");
                ciudades.add("Cacahual");
                ciudades.add("Pana Pana");
                break;

            case "Guaviare":
                ciudades.add("San José del Guaviare");
                ciudades.add("Calamar");
                ciudades.add("El Retorno");
                ciudades.add("Miraflores");
                break;

            case "Huila":
                ciudades.add("Neiva");
                ciudades.add("Pitalito");
                ciudades.add("Garzón");
                ciudades.add("La Plata");
                ciudades.add("Campoalegre");
                break;

            case "La Guajira":
                ciudades.add("Riohacha");
                ciudades.add("Maicao");
                ciudades.add("Uribia");
                ciudades.add("Fonseca");
                ciudades.add("San Juan del Cesar");
                break;
            case "Magdalena":
                ciudades.add("Santa Marta");
                ciudades.add("Ciénaga");
                ciudades.add("Fundación");
                ciudades.add("Aracataca");
                ciudades.add("El Banco");
                break;

            case "Meta":
                ciudades.add("Villavicencio");
                ciudades.add("Acacías");
                ciudades.add("Granada");
                ciudades.add("Puerto López");
                ciudades.add("Restrepo");
                break;

            case "Nariño":
                ciudades.add("Pasto");
                ciudades.add("Tumaco");
                ciudades.add("Ipiales");
                ciudades.add("Túquerres");
                ciudades.add("La Unión");
                break;

            case "Norte de Santander":
                ciudades.add("Cúcuta");
                ciudades.add("Ocaña");
                ciudades.add("Pamplona");
                ciudades.add("Villa del Rosario");
                ciudades.add("Los Patios");
                break;

            case "Putumayo":
                ciudades.add("Mocoa");
                ciudades.add("Puerto Asís");
                ciudades.add("Villagarzón");
                ciudades.add("Orito");
                ciudades.add("Sibundoy");
                break;

            case "Quindío":
                ciudades.add("Armenia");
                ciudades.add("Calarcá");
                ciudades.add("Montenegro");
                ciudades.add("Quimbaya");
                ciudades.add("La Tebaida");
                break;
            case "Risaralda":
                ciudades.add("Pereira");
                ciudades.add("Dosquebradas");
                ciudades.add("Santa Rosa de Cabal");
                ciudades.add("La Virginia");
                ciudades.add("Belén de Umbría");
                break;

            case "San Andrés y Providencia":
                ciudades.add("San Andrés");
                ciudades.add("Providencia");
                ciudades.add("Santa Catalina");
                break;

            case "Santander":
                ciudades.add("Bucaramanga");
                ciudades.add("Floridablanca");
                ciudades.add("Giron");
                ciudades.add("Piedecuesta");
                ciudades.add("Barrancabermeja");
                break;

            case "Sucre":
                ciudades.add("Sincelejo");
                ciudades.add("Corozal");
                ciudades.add("Sampués");
                ciudades.add("San Marcos");
                ciudades.add("Tolú");
                break;

            case "Tolima":
                ciudades.add("Ibagué");
                ciudades.add("Espinal");
                ciudades.add("Melgar");
                ciudades.add("Honda");
                ciudades.add("Lérida");
                break;

            case "Valle del Cauca":
                ciudades.add("Cali");
                ciudades.add("Palmira");
                ciudades.add("Buenaventura");
                ciudades.add("Tuluá");
                ciudades.add("Buga");
                break;

            case "Vaupés":
                ciudades.add("Mitú");
                ciudades.add("Carurú");
                ciudades.add("Taraira");
                ciudades.add("Pacoa");
                ciudades.add("Papunaua");
                break;

            case "Vichada":
                ciudades.add("Puerto Carreño");
                ciudades.add("La Primavera");
                ciudades.add("Santa Rosalía");
                ciudades.add("Cumaribo");
                break;

            default:
                ciudades.add("Seleccione un departamento válido");
                break;
        }
        return ciudades;
    }

    public static List<String> obtenerDepartamentos() {
        List<String> departamentos = new ArrayList<>();

        departamentos.add("");
        departamentos.add("Amazonas");
        departamentos.add("Antioquia");
        departamentos.add("Arauca");
        departamentos.add("Atlántico");
        departamentos.add("Bolívar");
        departamentos.add("Boyacá");
        departamentos.add("Caldas");
        departamentos.add("Caquetá");
        departamentos.add("Casanare");
        departamentos.add("Cauca");
        departamentos.add("Cesar");
        departamentos.add("Chocó");
        departamentos.add("Córdoba");
        departamentos.add("Cundinamarca");
        departamentos.add("Guainía");
        departamentos.add("Guaviare");
        departamentos.add("Huila");
        departamentos.add("La Guajira");
        departamentos.add("Magdalena");
        departamentos.add("Meta");
        departamentos.add("Nariño");
        departamentos.add("Norte de Santander");
        departamentos.add("Putumayo");
        departamentos.add("Quindío");
        departamentos.add("Risaralda");
        departamentos.add("San Andrés y Providencia");
        departamentos.add("Santander");
        departamentos.add("Sucre");
        departamentos.add("Tolima");
        departamentos.add("Valle del Cauca");
        departamentos.add("Vaupés");
        departamentos.add("Vichada");

        return departamentos;
    }

    public static void cargarDepartamentosEnCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        List<String> departamentos = obtenerDepartamentos();
        for (String dep : departamentos) {
            combo.addItem(dep);
        }
    }
}
