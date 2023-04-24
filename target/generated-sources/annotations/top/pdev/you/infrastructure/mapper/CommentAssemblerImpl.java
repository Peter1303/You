package top.pdev.you.infrastructure.mapper;

import javax.annotation.Generated;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.web.comment.command.CommentInfoCommand;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-24T17:20:33+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
public class CommentAssemblerImpl implements CommentAssembler {

    @Override
    public CommentInfoCommand convert(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentInfoCommand commentInfoCommand = new CommentInfoCommand();

        commentInfoCommand.setId( comment.getId() );
        commentInfoCommand.setComment( comment.getComment() );
        commentInfoCommand.setTime( comment.getTime() );

        return commentInfoCommand;
    }
}
