
package es.ait.mongoblog.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository with the persistence of Entry elements.
 *
 */
public interface EntryRepository extends MongoRepository<Entry, String> 
{
	Page<Entry> findByBlogAndPublishDateBeforeOrderByPublishDateDesc( String author, Date publishDate, Pageable pageable );
	List<Entry> findByBlogAndPublishDateAfterOrderByPublishDateAsc( String blog, Date publishDate );
	List<Entry> findByAuthorAndPublishDateAfterOrderByPublishDateAsc( String author, Date publishDate );
}
