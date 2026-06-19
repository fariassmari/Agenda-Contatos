
package repositorio.jpa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
    private static EntityManager manager;
    private static EntityManagerFactory factory;
    private static final Logger logger = LogManager.getLogger(Util.class);

    public static EntityManager conectar() throws Exception {
        if (manager != null)
            return manager;
        else {
            try {
                logger.info("-------- vai conectar com banco de dados postgresql");

                factory = Persistence.createEntityManagerFactory("hibernate-postgresql");

                manager = factory.createEntityManager();
                logger.info("-------- conectou banco postgresql");
                System.out.println("conectou ao banco postgresql ");

            } catch (Exception e) {
                logger.info("Problema ao conectar ao banco de dados postgresql: ");
                throw new Exception("Erro ao conectar ao banco db4o remoto \n" + e.getMessage());
            }
            return manager;
        }
    }

    public static void desconectar() throws Exception {
        try{
            if (manager != null && manager.isOpen()) {
                manager.close();
                factory.close();
                manager = null;
                logger.debug("-------- desconectou banco postgresql");
                System.out.println("desconectou ao banco postgresql ");
            }
        } catch(Exception e) {
            throw new Exception("Problema ao desconectar ao banco postgres" );
        }

    }
}