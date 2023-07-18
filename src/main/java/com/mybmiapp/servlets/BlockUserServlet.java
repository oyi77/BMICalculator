
import com.mybmiapp.dao.BlockedUserDao;
import com.mybmiapp.dao.BlockedUserDaoImpl;
import com.mybmiapp.models.BlockedUser;
import com.mybmiapp.models.User;
import com.mybmiapp.util.DatabaseConnection;
import com.mybmiapp.util.UserSessionUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/blockUser")
public class BlockUserServlet extends HttpServlet {

    private final BlockedUserDao blockedUserDao;

    public BlockUserServlet() throws SQLException {
        this.blockedUserDao = new BlockedUserDaoImpl(DatabaseConnection.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = UserSessionUtil.getCurrentUser(request.getSession());

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String blockedUserIdParam = request.getParameter("blockedUserId");

        if (blockedUserIdParam != null && !blockedUserIdParam.isEmpty()) {
            int blockedUserId = Integer.parseInt(blockedUserIdParam);
            BlockedUser block = new BlockedUser(0,currentUser.getId(), blockedUserId);
            blockedUserDao.blockUser(block);
        }

        response.sendRedirect("chat");
    }
}
