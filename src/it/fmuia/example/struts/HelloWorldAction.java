package it.fmuia.example.struts;

import it.fmuia.example.hibernate.Customer;
import it.fmuia.example.hibernate.HibernatePlugin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HelloWorldAction extends Action
{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SessionFactory sessionFactory = (SessionFactory) servlet.getServletContext().getAttribute(HibernatePlugin.KEY_NAME);
		Session session = sessionFactory.openSession();
		Customer customer = new Customer(1, "Hello", "world", new Date());
		session.beginTransaction();
		session.save(customer);
		session.getTransaction().commit();

		Query query = session.createQuery("Select c From Customer c where c.customerId = 1");
		List<Customer> results = query.list();
		Customer myCustomer = results.get(0);
		HelloWorldForm helloWorldForm = (HelloWorldForm) form;
		helloWorldForm.setMessage("Salvato customer " + myCustomer.getName() + " " + myCustomer.getAddress());
		session.close();
		return mapping.findForward("success");
	}

}