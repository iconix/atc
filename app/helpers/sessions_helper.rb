module SessionsHelper

	def sign_in(business)
    cookies.permanent[:remember_token] = business.remember_token
    self.current_business = business
  end

	def current_business=(business)
    @current_business = business
  end

	def current_business
    @current_business ||= Business.find_by_remember_token(cookies[:remember_token])
  end

	def current_business?(business)
		business == current_business
	end

	def signed_in_business
		unless signed_in?
			store_location
			redirect_to signin_url, notice: "Please sign in."
		end
	end

	def signed_in?
    !current_business.nil?
  end
	
	def sign_out
    self.current_business = nil
    cookies.delete(:remember_token)
  end

	def redirect_back_or(default)
		redirect_to(session[:return_to] || default)
		session.delete(:return_to)
	end

	def store_location
		session[:return_to] = request.url
	end
end
