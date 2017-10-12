package tv.huan.master.common.service;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.entity.BaseEntity;
import tv.huan.master.common.enums.SearchOperator;
import tv.huan.master.common.model.EasyUiGridResponse;
import tv.huan.master.common.model.EasyUiPageRequest;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.util.BeanUtils;

/**
 * Created by IntelliJ IDEA.
 * Ts: warriorr
 * Mail: warriorr@163.com
 * QQ:283173481
 * Date: 11-12-14
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates
 */
@Service
public abstract class BaseService<M extends BaseEntity> {
    @Autowired
    protected BaseDAO baseDAO;
    Class<M> clazz; 
    protected Class<M> baseClass()
	{
		if(clazz == null)
		{
			  Type genType = this.getClass().getGenericSuperclass();
		      Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		      clazz = (Class<M>) params[0];
		}
		return clazz;
	}

    public List<M> findAll() {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        return baseDAO.find(crit);
    }

    public int findTotal(Map<String, String> map) {
        return baseDAO.findTotal(this.getcrit(map));
    }

    public List<M> find(Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.addOrder(Order.desc("updateDate"));
        return baseDAO.find(crit);
    }

    public List<M> find(int page_num, int per_page, Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.addOrder(Order.desc("updateDate"));
        return baseDAO.find(page_num, per_page, crit);
    }
    public List<M> findRange(int page_num, int per_page, Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.addOrder(Order.desc("id"));
        return baseDAO.findRange(page_num, per_page, crit);
    }
    public List<M> findRange(int page_num, int per_page, Map<String, String> map,String order) {
        DetachedCriteria crit = this.getcrit(map);
        if(order!=null&&order.trim().length()>0)
	       crit.addOrder(Order.desc(order));
	       crit.addOrder(Order.desc("id"));
        return baseDAO.findRange(page_num, per_page, crit);
    }
    public EasyUiGridResponse find(EasyUiPageRequest easyUiPageRequest, Searchable searchable) {
        DetachedCriteria crit = this.getcrit(searchable.getMap());
        if (easyUiPageRequest.getOrder().length() > 0 && easyUiPageRequest.getSort().length() > 0) {
            if (easyUiPageRequest.getSort().equals("desc")) {
                crit.addOrder(Order.desc(easyUiPageRequest.getOrder()));
            } else {
                crit.addOrder(Order.desc(easyUiPageRequest.getSort()));
            }
        } else {
            crit.addOrder(Order.desc("updateDate"));
        }
        return baseDAO.find(easyUiPageRequest, crit);
    }

    public DetachedCriteria getcrit(Map<String, String> map) {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String key = entry.getKey().split("_")[0];
//            SearchOperator operator = SearchOperator.valueOf(entry.getKey().split("_")[1]);
            String tempkey = entry.getKey();
            String key = tempkey.substring(0, tempkey.lastIndexOf("_"));
            SearchOperator operator = SearchOperator.valueOf(tempkey.substring(tempkey.lastIndexOf("_") + 1));
            String value = entry.getValue();
            if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
                value = "%" + value + "%";
            }
            if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
                value = value + "%";
            }

            if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
                value = "%" + value;
            }
            if (key.indexOf(".") > 0) {
                String[] keys = key.split("\\.");
                crit.createCriteria(keys[0]).add(Restrictions.sqlRestriction("{alias}." + keys[1] + " " + operator.getSymbol() + " ?", value, StringType.INSTANCE));
            } else {
                crit.add(Restrictions.sqlRestriction("{alias}." + key + " " + operator.getSymbol() + " ?", value, StringType.INSTANCE));
            }
        }
        return crit;
    }

    public M findById(Long id) {
        return (M)baseDAO.load(baseClass(), id);
    }

    public M save(M base){
        base.setUpdateDate(new Date());
    	if(base.getId()!=null)
    	{
    		M dbase=findById(base.getId());
    		if(dbase!=null){
    		BeanUtils.copyProperties(base, dbase);
    		baseDAO.save(dbase);
    		return dbase;
    		}
    	}
    	baseDAO.save(base);
    	return base;
    }
    public M saveBase(M base){
    	baseDAO.save(base);
    	return base;
    }
    public void del(Long id) {
        M baseEntity = (M)baseDAO.load(baseClass(), id);
        baseDAO.delete(baseEntity);
    }

    public void del_logic(Long id) {
        M baseEntity = (M)baseDAO.load(baseClass(), id);
        baseEntity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
        baseDAO.update(baseEntity);
    }

    public void del(String ids) {
        baseDAO.delete(baseClass(), ids);
    }
    public void delAll() {
        baseDAO.delete(baseClass());
    }
    public void del_logic(String ids) {
        baseDAO.logic_delete(baseClass(), ids);
    }
}