class SessionsController < ApplicationController
	def new
  end

  def create
		business = Business.find_by_email(params[:session][:email].downcase)
    if business && business.authenticate(params[:session][:password])
			sign_in business
			redirect_to business
    else
      flash.now[:error] = 'Invalid email/password combination' # Not quite right!
      render 'new'
    end
  end

  def destroy
		sign_out
    redirect_to root_url
  end

end
