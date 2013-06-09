namespace :db do
  desc "Add admin accounts to database"
  task admins: :environment do

    business = Business.create!(name: "SAPenguins",
                     email: "admin@atc.com",
                     password: "sapenguins",
                     password_confirmation: "sapenguins")
    business.toggle!(:admin)
  end
end
