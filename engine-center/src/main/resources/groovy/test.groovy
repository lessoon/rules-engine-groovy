import cn.hutool.db.Entity;
import com.reda.engine.center.common.AbstractDataBaseClient;

def test(String tableName) {
    Entity entity = new Entity(); entity.setTableName(tableName); return AbstractDataBaseClient.use.findAll(entity);
}
