package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.componentes.FechasComponente;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;
import vista.componentes.ScrollPersonalizado;

public class PaginaInformeGlobal extends JFrame {
        private Container contenedor;
        private Header header;
        public FechasComponente containFechas;
        public JPanel contenedorInfo;
        public JLabel contenido;
        private Fuente fuente = new Fuente();
        private RoundedPanel totalVenta, totalIngresos, productoMasVendido, prodMenosVendido;
        public JLabel textTotalVenta, textTotalIngreso, textProdMasVen, textProdMenVen;
        public RoundedPanel containProdMenosVen, containProdMasVen, containStockCategoria;
        private RoundedPanel containMayorTarjeta, containMayorTarjetaMenos, containMayorStock;
        public RoundedPanel containClienteRegis, containTendeCompra, containCantiPedido, containMetoPago;
        public JLabel textClienteRegis, textTendeCom, textCantiPedi, textMetoPago;

        public PaginaInformeGlobal() {
                super("Informe Globales");
                contenedor = getContentPane();
                contenedor.setLayout(null);
                contenedor.setBackground(Color.white);

                // agregar el header
                header = new Header();
                contenedor.add(header);

                // agregar el contenedor para ingresar la fecha y los botones
                containFechas = new FechasComponente(10, 0xFFFFFF, "Generar Informe", "Informe Globales");
                // containFechas.agregarBtnDescargarInforme("Descargar Informe");
                containFechas.setBounds(10, 120, 200, 210);
                contenedor.add(containFechas);

                // agregar el contenedor para mostrar la informacion
                contenedorInfo = new JPanel();
                // contenedorInfo.setBounds(280, containFechas.getY(), 950, 1500);
                contenedorInfo.setBounds(0, 0, 950, 2200);
                contenedorInfo.setLayout(null);

                // agregar el componente para el total de venta
                totalVenta = new RoundedPanel(10, 0xE1DADA);
                totalVenta.setShadowSize(1);
                totalVenta.setLayout(null);
                totalVenta.setBackground(new Color(0xE1DADA));
                JLabel titleTVenta = new JLabel("Total de Venta Realizadas");
                titleTVenta.setFont(fuente.fuente(4, true));
                titleTVenta.setBounds(10, 10, 300, 20);
                totalVenta.add(titleTVenta);
                textTotalVenta = new JLabel();
                textTotalVenta.setFont(fuente.fuente(5, true));
                textTotalVenta.setBounds(10, 40, 300, 20);
                totalVenta.add(textTotalVenta);
                totalVenta.setBounds(40, 10, 400, 80);
                contenedorInfo.add(totalVenta);

                // agregar el componente para el
                totalIngresos = new RoundedPanel(10, 0xE1DADA);
                totalIngresos.setShadowSize(1);
                totalIngresos.setLayout(null);
                totalIngresos.setBackground(new Color(0xE1DADA));
                JLabel titleTIngre = new JLabel("Total de Ingresos Generados");
                titleTIngre.setFont(fuente.fuente(4, true));
                titleTIngre.setBounds(10, 10, 300, 20);
                totalIngresos.add(titleTIngre);
                textTotalIngreso = new JLabel();
                textTotalIngreso.setFont(fuente.fuente(5, true));
                textTotalIngreso.setBounds(10, 40, 300, 20);
                totalIngresos.add(textTotalIngreso);
                totalIngresos.setBounds(500, 10, 400, 80);
                contenedorInfo.add(totalIngresos);

                // agregar producto mas vendido
                productoMasVendido = new RoundedPanel(10, 0xE1DADA);
                productoMasVendido.setShadowSize(1);
                productoMasVendido.setLayout(null);
                productoMasVendido.setBackground(new Color(0xE1DADA));
                JLabel titleProdMVen = new JLabel("Producto Mas Vendido");
                titleProdMVen.setFont(fuente.fuente(4, true));
                titleProdMVen.setBounds(10, 10, 300, 20);
                productoMasVendido.add(titleProdMVen);
                textProdMasVen = new JLabel();
                textProdMasVen.setFont(fuente.fuente(5, true));
                textProdMasVen.setBounds(10, 40, 300, 20);
                productoMasVendido.add(textProdMasVen);
                productoMasVendido.setBounds(totalVenta.getX(), 110, 400, 80);
                contenedorInfo.add(productoMasVendido);

                // agregar producto Menos Vendidos
                prodMenosVendido = new RoundedPanel(10, 0xE1DADA);
                prodMenosVendido.setShadowSize(1);
                prodMenosVendido.setLayout(null);
                prodMenosVendido.setBackground(new Color(0xE1DADA));
                JLabel titleProdMenVen = new JLabel("Producto Menos Vendido");
                titleProdMenVen.setFont(fuente.fuente(4, true));
                titleProdMenVen.setBounds(10, 10, 300, 20);
                prodMenosVendido.add(titleProdMenVen);
                textProdMenVen = new JLabel();
                textProdMenVen.setFont(fuente.fuente(5, true));
                textProdMenVen.setBounds(10, 40, 300, 20);
                prodMenosVendido.add(textProdMenVen);
                prodMenosVendido.setBounds(totalIngresos.getX(), productoMasVendido.getY(), 400, 80);
                contenedorInfo.add(prodMenosVendido);

                // Contenedor mayor para los productos mas vendidos
                containMayorTarjeta = new RoundedPanel(20, 0xFFFFFF);
                containMayorTarjeta.setBackground(new Color(0xF8F9FB));
                containMayorTarjeta.setBounds(5, prodMenosVendido.getY() + 110, 940, 560);
                containMayorTarjeta.setLayout(null);
                JLabel textProductoMasVendido = new JLabel("Productos Mas Vendidos");
                textProductoMasVendido.setFont(fuente.fuente(2, true));
                textProductoMasVendido.setBounds(180, 10, 700, 40);
                containMayorTarjeta.add(textProductoMasVendido);
                containMayorTarjeta.setShadowSize(1);
                // agregar el contenedor para los 10 productos mas vendidos
                containProdMasVen = new RoundedPanel(10, 0xF8F9FB);
                containProdMasVen.setBackground(new Color(0xF8F9FB));
                containProdMasVen.setShadowSize(1);
                containProdMasVen.setBounds(0, 50, 940, 510);
                containMayorTarjeta.add(containProdMasVen);
                contenedorInfo.add(containMayorTarjeta);

                // Contenedor mayor para los productos mas vendidos
                containMayorTarjetaMenos = new RoundedPanel(20, 0xFFFFFF);
                containMayorTarjetaMenos.setBackground(new Color(0xF8F9FB));
                containMayorTarjetaMenos.setBounds(5,
                                (int) (containMayorTarjeta.getY() + containMayorTarjeta.getHeight() + 20),
                                940, 560);
                containMayorTarjetaMenos.setLayout(null);
                JLabel textProductoMenVendido = new JLabel("Productos Menos Vendidos");
                textProductoMenVendido.setFont(fuente.fuente(2, true));
                textProductoMenVendido.setBounds(180, 10, 700, 40);
                containMayorTarjetaMenos.add(textProductoMenVendido);
                containMayorTarjetaMenos.setShadowSize(1);
                // agregar el contenedor para los 10 productos menos vendidos
                containProdMenosVen = new RoundedPanel(10, 0xF8F9FB);
                containProdMenosVen.setShadowSize(1);
                containProdMenosVen.setBackground(new Color(0xF8F9FB));
                containProdMenosVen.setBounds(0, 50, 940, 510);
                containMayorTarjetaMenos.add(containProdMenosVen);
                contenedorInfo.add(containMayorTarjetaMenos);

                // Agregar el mayor para el stock por producto
                containMayorStock = new RoundedPanel(20, 0xFFFFFF);
                containMayorStock.setLayout(null);
                containMayorStock.setBackground(new Color(0xF8F9FB));
                containMayorStock.setBounds(5,
                                (int) (containMayorTarjetaMenos.getY() + containMayorTarjetaMenos.getHeight() + 20),
                                940, 560);
                JLabel titleStock = new JLabel("Stock actual por Productos");
                titleStock.setFont(fuente.fuente(2, true));
                titleStock.setBounds(180, 10, 700, 40);
                containMayorStock.add(titleStock);
                containMayorStock.setShadowSize(1);

                // Agregar el contenedor para las tarjetas de Stocks
                containStockCategoria = new RoundedPanel(10, 0xFFFFFF);
                containStockCategoria.setBackground(new Color(0xF8F9FB));
                containStockCategoria.setShadowSize(1);
                containStockCategoria.setBounds(0, 50, 940, 510);
                containMayorStock.add(containStockCategoria);
                contenedorInfo.add(containMayorStock);

                // agregar numero de cliente registrado
                containClienteRegis = new RoundedPanel(10, 0xFFFFFF);
                containClienteRegis.setShadowSize(1);
                containClienteRegis.setLayout(null);
                containClienteRegis.setBackground(new Color(0xF8F9FB));
                JLabel titleCliRegi = new JLabel("Numero de clientes Registrados");
                titleCliRegi.setFont(fuente.fuente(4, true));
                titleCliRegi.setBounds(10, 10, 330, 20);
                containClienteRegis.add(titleCliRegi);
                textClienteRegis = new JLabel();
                textClienteRegis.setFont(fuente.fuente(5, true));
                textClienteRegis.setBounds(10, 40, 300, 20);
                containClienteRegis.add(textClienteRegis);
                containClienteRegis.setBounds(totalVenta.getX(),
                                (int) (containMayorStock.getY() + containMayorStock.getHeight() + 20),
                                totalVenta.getWidth(),
                                totalVenta.getHeight());
                contenedorInfo.add(containClienteRegis);

                // agregar tendencia de compra
                containTendeCompra = new RoundedPanel(10, 0xFFFFFF);
                containTendeCompra.setShadowSize(1);
                containTendeCompra.setLayout(null);
                containTendeCompra.setBackground(new Color(0xF8F9FB));
                JLabel titleTenCom = new JLabel("Tendencias de compra");
                titleTenCom.setFont(fuente.fuente(4, true));
                titleTenCom.setBounds(10, 10, 330, 20);
                containTendeCompra.add(titleTenCom);
                textTendeCom = new JLabel("");
                textTendeCom.setFont(fuente.fuente(5, true));
                textTendeCom.setBounds(10, 40, 300, 20);
                containTendeCompra.add(textTendeCom);
                containTendeCompra.setBounds(totalIngresos.getX(),
                                (int) (containMayorStock.getY() + containMayorStock.getHeight() + 20),
                                totalVenta.getWidth(),
                                totalVenta.getHeight());
                contenedorInfo.add(containTendeCompra);

                // agregar numero clientes totales
                containCantiPedido = new RoundedPanel(10, 0xFFFFFF);
                containCantiPedido.setShadowSize(1);
                containCantiPedido.setLayout(null);
                containCantiPedido.setBackground(new Color(0xF8F9FB));
                JLabel titleTenComP = new JLabel("Cantidad total de pedidos");
                titleTenComP.setFont(fuente.fuente(4, true));
                titleTenComP.setBounds(10, 10, 330, 20);
                containCantiPedido.add(titleTenComP);
                textCantiPedi = new JLabel();
                textCantiPedi.setFont(fuente.fuente(5, true));
                textCantiPedi.setBounds(10, 40, 300, 20);
                containCantiPedido.add(textCantiPedi);
                containCantiPedido.setBounds(totalVenta.getX(),
                                (int) (containClienteRegis.getY() + containClienteRegis.getHeight() + 20),
                                totalVenta.getWidth(),
                                totalVenta.getHeight());
                contenedorInfo.add(containCantiPedido);

                // agregar tendencia de compra
                containMetoPago = new RoundedPanel(10, 0xFFFFFF);
                containMetoPago.setShadowSize(1);
                containMetoPago.setLayout(null);
                containMetoPago.setBackground(new Color(0xF8F9FB));
                JLabel titleMetodoP = new JLabel("Metodo de pago mas utilizado");
                titleMetodoP.setFont(fuente.fuente(4, true));
                titleMetodoP.setBounds(10, 10, 330, 20);
                containMetoPago.add(titleMetodoP);
                textMetoPago = new JLabel();
                textMetoPago.setFont(fuente.fuente(5, true));
                textMetoPago.setBounds(10, 40, 300, 20);
                containMetoPago.add(textMetoPago);
                containMetoPago.setBounds(totalIngresos.getX(),
                                (int) (containClienteRegis.getY() + containClienteRegis.getHeight() + 20),
                                totalVenta.getWidth(),
                                totalVenta.getHeight());
                contenedorInfo.add(containMetoPago);

                contenedorInfo.setBackground(Color.white);
                ScrollPersonalizado scrollConteInfo = new ScrollPersonalizado(contenedorInfo, "vertical", 940, 500);
                scrollConteInfo.setBounds(280, containFechas.getY(), 950, 500);

                contenedorInfo.setVisible(false);
                contenedor.add(scrollConteInfo);
        }

        public void modificarTamañoStock(int Largo) {
                containMayorStock.setBounds(5,
                                (int) (containMayorTarjetaMenos.getY() + containMayorTarjetaMenos.getHeight() + 20),
                                940, Largo + 20);
                containMayorStock.revalidate();
                containMayorStock.repaint();
                containStockCategoria.setBounds(5,
                                (int) (containMayorTarjetaMenos.getY() + containMayorTarjetaMenos.getHeight() + 20),
                                940, Largo);
                containStockCategoria.revalidate();
                containStockCategoria.repaint();
                contenedorInfo.setBounds(0, 0, 1300, 2200 + Largo);
                contenedorInfo.revalidate();
                contenedorInfo.repaint();
        }

        public void modificarTamañoFecha(int Largo) {
                // 260 o 270 de largo
                // ocultar 210
                containFechas.setSize(new Dimension(200, Largo));
        }
}
