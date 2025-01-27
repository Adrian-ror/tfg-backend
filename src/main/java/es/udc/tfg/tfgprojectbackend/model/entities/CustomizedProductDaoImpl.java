package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

/**
 * Custom implementation of CustomizedProductDao for accessing products.
 */
public class CustomizedProductDaoImpl implements CustomizedProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts a string of keywords into an array of tokens.
     *
     * @param keywords the string of keywords to split
     * @return an array of tokens extracted from the keywords
     */
    private String[] getTokens(String keywords) {
        if (keywords == null || keywords.length() == 0) {
            return new String[0];
        } else {
            return keywords.split("\\s");
        }
    }

    /**
     * Searches for products filtering by category, keywords, and rating.
     *
     * @param categoryId the ID of the category to filter
     * @param keywords   the keywords to search for products
     * @param minRating  the minimum rating that the products must have
     * @param page       the page number for pagination
     * @param size       the page size
     * @return a Slice of products that meet the search criteria
     */
    @SuppressWarnings("unchecked")
    @Override
    public Slice<Product> find(Long categoryId, String keywords, Double minRating, int page, int size) {
        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Product p WHERE p.isVisible = true";


        if (categoryId != null) {
            queryString += " AND p.category.id IN (SELECT c.id FROM Category c WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId)";
        }

        if (minRating != null) {
            queryString += " AND p.rating >= :minRating";
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                queryString += " AND LOWER(p.name) LIKE LOWER(:token" + i + ")";
            }
        }

        queryString += " ORDER BY p.name";

        Query query = entityManager.createQuery(queryString)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size + 1);

        if (hasNext) {
            products.remove(products.size() - 1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }



    /**
     * Counts the total number of products that meet the search criteria.
     *
     * @param categoryId the ID of the category to filter
     * @param keywords   the keywords to search for products
     * @param minRating  the minimum rating that the products must have
     * @return the total number of products that meet the criteria
     */
    @Override
    public long count(Long categoryId, String keywords, Double minRating) {
        String[] tokens = getTokens(keywords);
        String queryString = "SELECT COUNT(p) FROM Product p WHERE p.isVisible = true";

        if (categoryId != null) {
            queryString += " AND p.category.id IN (SELECT c.id FROM Category c WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId)";
        }

        if (minRating != null) {
            queryString += " AND p.rating >= :minRating";
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                queryString += " AND LOWER(p.name) LIKE LOWER(:token" + i + ")";
            }
        }

        queryString += " ORDER BY p.name";

        Query query = entityManager.createQuery(queryString);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        return (long) query.getSingleResult();
    }



    @SuppressWarnings("unchecked")
    @Override
    public Slice<Product> findAllProducts(Long categoryId, String keywords, Double minRating, int page, int size) {
        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Product p";

        if (categoryId != null || minRating != null || tokens.length > 0) {
            queryString += " WHERE ";
        }

        if (categoryId != null) {
            queryString += " p.category.id IN (SELECT c.id FROM Category c WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId)";
        }

        if (minRating != null) {
            if (categoryId != null) {
                queryString += " AND ";
            }
            queryString += " p.rating >= :minRating";
        }

        if (tokens.length != 0) {
            if (categoryId != null || minRating != null) {
                queryString += " AND ";
            }
            for (int i = 0; i < tokens.length; i++) {
                queryString += " LOWER(p.name) LIKE LOWER(:token" + i + ")";
            }
        }

        queryString += " ORDER BY p.name";

        Query query = entityManager.createQuery(queryString)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size + 1);

        if (hasNext) {
            products.remove(products.size() - 1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }

    @SuppressWarnings("unchecked")
    @Override
    public long countAllProducts(Long categoryId, String keywords, Double minRating) {
        String[] tokens = getTokens(keywords);
            String queryString = "SELECT COUNT(p) FROM Product p";

        if (categoryId != null || minRating != null || tokens.length > 0) {
            queryString += " WHERE ";
        }

        if (categoryId != null) {
            queryString += " p.category.id IN (SELECT c.id FROM Category c WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId)";
        }

        if (minRating != null) {
            if (categoryId != null) {
                queryString += " AND ";
            }
            queryString += " p.rating >= :minRating";
        }

        if (tokens.length != 0) {
            if (categoryId != null || minRating != null) {
                queryString += " AND ";
            }
            for (int i = 0; i < tokens.length; i++) {
                queryString += " LOWER(p.name) LIKE LOWER(:token" + i + ")";
            }
        }

        queryString += " ORDER BY p.name";

        Query query = entityManager.createQuery(queryString);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }

        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        return (long) query.getSingleResult();
    }

}
