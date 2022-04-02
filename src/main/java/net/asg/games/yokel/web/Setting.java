package net.asg.games.yokel.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    private String label;
    private boolean value;
}