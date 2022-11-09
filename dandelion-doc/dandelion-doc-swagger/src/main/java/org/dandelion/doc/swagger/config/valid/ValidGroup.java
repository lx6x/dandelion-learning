package org.dandelion.doc.swagger.config.valid;

import javax.validation.groups.Default;

/**
 * TODO 分组校验接口
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 14:19
 */
public interface ValidGroup extends Default {
    interface Crud extends ValidGroup{
        interface Create extends Crud{

        }

        interface Update extends Crud{

        }

        interface Query extends Crud{

        }

        interface Delete extends Crud{

        }
    }

}