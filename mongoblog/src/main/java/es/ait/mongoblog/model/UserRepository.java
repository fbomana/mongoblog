package es.ait.mongoblog.model;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> 
{
	public User findOneByNick( String nick );
	public User findOneByNickOrEmail( String nick, String email );
	public User findOneByEmailOrInviteEmail( String email, String inviteEmail );
	
	@Query(value="{ 'editors' : ?0 }")	
	public List<User>findBlogsEditedBy( String user );
}
