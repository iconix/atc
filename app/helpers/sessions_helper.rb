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

	def signed_in?
    !current_business.nil?
  end
	
	def sign_out
    self.current_business = nil
    cookies.delete(:remember_token)
  end
end
