package kz.itbc.docshare.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class LeftMenuServiceAJAX implements Service{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter(ACTION_PARAMETER);
        int opened_menu_id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        HttpSession session = req.getSession();
        switch (action){
            case ADD_TO_OPEN_MENU_LIST_VALUE:{
                addToOpenMenuList(session, opened_menu_id);
                break;
            }
            case DELETE_FROM_OPEN_MENU_LIST_VALUE: {
                deleteFromOpenMenuList(session, opened_menu_id);
                break;
            }
        }
    }

    private void addToOpenMenuList(HttpSession session, int opened_menu_id){
        @SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED_VALUE)
        List<Integer> opened_menu_ids = (ArrayList<Integer>)session.getAttribute(OPENED_MENU_ATTRIBUTE);
        if(opened_menu_ids == null){
            opened_menu_ids = new ArrayList<>();
            opened_menu_ids.add(opened_menu_id);
            session.setAttribute(OPENED_MENU_ATTRIBUTE, opened_menu_ids);
        } else {
            boolean is_has_this_value = false;
            for (int id : opened_menu_ids) {
                if (opened_menu_id == id){
                    is_has_this_value = true;
                    break;
                }
            }
            if(!is_has_this_value){
                opened_menu_ids.add(opened_menu_id);
            }
        }
    }

    private void deleteFromOpenMenuList(HttpSession session, int opened_menu_id){
        @SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED_VALUE)
        List<Integer> opened_menu_ids = (ArrayList<Integer>)session.getAttribute(OPENED_MENU_ATTRIBUTE);
        if(opened_menu_ids == null){
            opened_menu_ids = new ArrayList<>();
            session.setAttribute(OPENED_MENU_ATTRIBUTE, opened_menu_ids);
        } else {
            boolean is_has_this_value = false;
            for (int id : opened_menu_ids) {
                if (opened_menu_id == id){
                    is_has_this_value = true;
                    break;
                }
            }
            if(is_has_this_value){
                opened_menu_ids.remove((Integer)opened_menu_id);
            }
        }
    }


}
