package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@Data
public class InvoiceConstant {
	

	final String INSERT_SQL = "insert into invoices (" +
	           
            "site_code," +
            "material_id," +
            "qunatity," +
            "total," +
            "is_approved_site_manager,"
            + "order_id)" +
            "values(?,?,?,?,?,?)";
	
	
	
	final String UPDATE_SQL = " update invoices set "
			+ "site_code = ?,"
			+ "material_id = ?,"
			+ "qunatity = ?,"
			+ "total = ?,"
			+ "is_approved_site_manager = ? ,"
			+ "order_id = ?"
			+ " where id = ?";
	
	
	final String FIND_ALL = "select i.*,m.name as matrial_name, m.unit_type, s.address, s.name as site_name,u.first_name as supplier_name "
			+ " from invoices i left join material m on i.material_id = m.id "
			+ "left join user u on u.id = m.supplier_id "
			+ "left join site s on i.site_code = s.id;";
	
	
	final String FIND_BY_ID = "select i.*,m.name as matrial_name, m.unit_type, s.address, s.name as site_name,u.first_name as supplier_name  "
			+ "from invoices i left join material m on i.material_id = m.id"
			+ " left join user u on u.id = m.supplier_id "
			+ "left join site s on i.site_code = s.id where i.id = ?;";
	
	
	
	final String DELETE = "delete from invoices where id = ?";

}
