package app.system.application.backend.constant;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrderConstant {

	final String INSERT_SQL = "insert into orders (" +
	           
            "material_id," +
            "quantity," +
            "total_cost," +
            "ordered_date," +
            "delivery_date," +
            "is_approve_status," +
            "site_id," +
            "quotation_status)" +
            "values(?,?,?,?,?,?,?,?)";

	final String UPDATE_SQL = " update orders set "
			+ "material_id = ?,"
			+ "quantity = ?,"
			+ "total_cost = ?,"
			+ "ordered_date = ?,"
			+ "delivery_date = ?,"
			+ "is_approve_status = ?,"
			+ "site_id = ?,"
			+ "quotation_status = ? "
			+ "where order_id = ?";
	
	
	final String FIND_ALL = "select o.*, "
			+ "u.first_name,"
			+ "u.last_name ,"
			+ "s.name as site_name,"
			+ "s.address as location,"
			+ "s.contact_number as site_phone_nummber,"
			+ "s.site_manager_id, "
			+ "m.name as material_name,"
			+ "m.supplier_id as supplier_id,"
			+ "m.unit_type "
			+ "  from orders o "
			+ "left join site s on o.site_id = s.id"
			+ " left join user u on "
			+ "u.id = s.site_manager_id "
			+ "left join material m on o.material_id =m.id";
	
	final String FIND_BY_ID = "select o.*,"
			+ " u.first_name,"
			+ "u.last_name ,"
			+ "s.name as site_name,"
			+ "s.address as location,"
			+ "s.site_manager_id,"
			+ "m.unit_type,"
			+ "m.name as material_name,"
			+ "m.supplier_id as supplier_id,"
			+ "s.contact_number as site_phone_nummber  "
			+ "from orders o"
			+ " left join site s on o.site_id = s.id "
			+ "left join user u on u.id = s.site_manager_id "
			+ "left join material m on o.material_id =m.id"
			+ " where o.order_id = ?";
	
	
	final String FIND_BY_DELIVERY_STATUS = "select o.*,"
			+ " u.first_name,"
			+ "u.last_name ,"
			+ "s.name as site_name,"
			+ "s.address as location,"
			+ "s.site_manager_id,"
			+ "m.unit_type,"
			+ "m.name as material_name,"
			+ "m.supplier_id as supplier_id,"
			+ "s.contact_number as site_phone_nummber  "
			+ "from orders o"
			+ " left join site s on o.site_id = s.id "
			+ "left join user u on u.id = s.site_manager_id "
			+ "left join material m on o.material_id =m.id"
			+ " where o.delivery_status = ?";
	
	final String DELETE = "delete from orders where order_id = ?";
	
	final String DELIVERY_STATUS_UPDATE =" update orders set delivery_status = ? where order_id = ? ";

	final String APPROVE_ORDER = "update orders set is_approve_status = ?, quotation_status = 2 where order_id = ? ";



}
